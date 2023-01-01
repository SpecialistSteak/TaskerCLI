package org.specialistSteak;

import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;

import static org.specialistSteak.Task.*;

@CommandLine.Command(
        name = "print",
        mixinStandardHelpOptions = true
)
public class PrintCommand implements Runnable {

    @CommandLine.Option(names = {"-s", "--search"}, description = "Show all tasks containing search term and exit.")
    private String searchTermString;

    @CommandLine.Option(names = {"-f", "--filter"}, description = "Only show incomplete tasks and exit.")
    private boolean filterBoolean;

    @CommandLine.Option(names = {"-o", "--order"}, description = "Order tasks by priority and exit.")
    private boolean orderBoolean;

    @CommandLine.Option(names = {"-P", "--PERMANENT"}, description = "Make changes (search, filter, order) permanent and exit.")
    private boolean isPermanent;

    @Override
    public void run() {
        try {
            loadTasks();
        } catch (IOException e) {
            try {
                saveTasks(tasks);
            } catch (IOException ex) {
                System.out.println("Error loading tasks file: " + e.getMessage());
                System.out.println("The program cannot run properly without the file. Please fix this.");
                System.out.println((e.getMessage().indexOf("Permission denied")>0) ? "You may need to use sudo privileges to edit the file." : "");
            }
        }
        ArrayList<Task> taskCopy = new ArrayList<Task>(tasks);

        if (searchTermString != null) {
            taskCopy = searchTasks(taskCopy, searchTermString);
        }
        if (filterBoolean) {
            ArrayList<Task> toRemove = new ArrayList<>();
            for (Task task : taskCopy) {
                if (task.isCompleted()) {
                    toRemove.add(task);
                }
            }
            taskCopy.removeAll(toRemove);
        }
        if (orderBoolean) {
            for (int i = 0; i < taskCopy.size() - 1; i++) {
                for (int j = i + 1; j < taskCopy.size(); j++) {
                    if (taskCopy.get(i).getPriority() > taskCopy.get(j).getPriority()){
                        Task temp = taskCopy.get(i);
                        taskCopy.set(i, taskCopy.get(j));
                        taskCopy.set(j, temp);
                    }
                }
            }
        }
        if (isPermanent) {
            tasks.clear();
            tasks.addAll(taskCopy);
            System.out.println("All changes have been permanently implemented.");
        }

        try {
            saveTasks(tasks);
        } catch (IOException e) {
            System.out.println("Error loading tasks file: " + e.getMessage());
            System.out.println("The program cannot run properly without the file. Please fix this.");
        }
        printTasks(taskCopy);
    }
}
