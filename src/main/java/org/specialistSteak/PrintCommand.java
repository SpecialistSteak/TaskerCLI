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
            saveTasks(tasks);
        }
        //try loading tasks, make file if it fails, if that fails, let user know

        ArrayList<Task> taskCopy = new ArrayList<>(tasks);
        //make an array for the copy of tasks (to allow for -P option)

        //if search was used, search tasks
        if (searchTermString != null) {
            taskCopy = searchTasks(taskCopy, searchTermString);
        }
        //filter out incomplete to temp List if used
        if (filterBoolean) {
            ArrayList<Task> toRemove = new ArrayList<>();
            for (Task task : taskCopy) {
                if (task.isCompleted()) {
                    toRemove.add(task);
                }
            }
            taskCopy.removeAll(toRemove);
        }
        //order to temp List if used
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

        //set old task List to new task List
        if (isPermanent) {
            tasks.clear();
            tasks.addAll(taskCopy);
            System.out.println("All changes have been permanently implemented.");
        }

        //try to save tasks, catch error
        saveTasks(tasks);
        printTasks(taskCopy);
    }
}
