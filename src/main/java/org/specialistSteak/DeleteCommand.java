package org.specialistSteak;

import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;

import static org.specialistSteak.Task.*;

@CommandLine.Command(
        name = "delete",
        mixinStandardHelpOptions = true
)
public class DeleteCommand implements Runnable {
    @CommandLine.Option(names = {"-s", "--search"}, description = "Delete all tasks containing search term and exit.")
    private String searchTermString;

    @CommandLine.Option(names = {"-i", "--index"}, description = "Delete task at given index and exit.")
    private Integer indexInteger;

    @CommandLine.Option(names = {"-c", "--complete"}, description = "Delete all complete tasks and exit.")
    private boolean completedBoolean;

    @CommandLine.Option(names = {"-ic", "--incomplete"}, description = "Delete all complete tasks and exit.")
    private boolean incompletedBoolean;

    @CommandLine.Option(names = {"-a", "--all"}, description = "Delete all tasks and exit.")
    private boolean allBoolean;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print list of tasks and exit.")
    private boolean printBoolean;

    @Override
    public void run() {
        try {
            loadTasks();
        }
        catch (IOException e){
            try {
                saveTasks(tasks);
            } catch (IOException ex) {
                System.out.println("Error loading tasks file: " + e.getMessage());
                System.out.println("The program cannot run properly without the file. Please fix this.");
            }
        }

        if(tasks.size() == 0){
            System.out.println("Try adding some tasks first.");
        }
        else {
            if (searchTermString != null) {
                tasks.removeAll(searchTasks(tasks, searchTermString));
                System.out.println("All tasks containing the search term have been deleted.");
            }
            if (indexInteger != null) {
                tasks.remove(indexInteger.intValue());
                System.out.printf("Task at index %d has been deleted\n", indexInteger);
            }
            if (completedBoolean) {
                ArrayList<Task> toRemove = new ArrayList<>();
                for (Task task : tasks) {
                    if (task.isCompleted()) {
                        toRemove.add(task);
                    }
                }
                tasks.removeAll(toRemove);
                System.out.println("All complete tasks successfully deleted.");
            }
            if (incompletedBoolean) {
                ArrayList<Task> toRemove = new ArrayList<>();
                for (Task task : tasks) {
                    if (!task.isCompleted()) {
                        toRemove.add(task);
                    }
                }
                tasks.removeAll(toRemove);
                System.out.println("All incomplete tasks successfully deleted.");
            }
            if (allBoolean) {
                tasks.clear();
                System.out.println("All tasks successfully deleted.");
            }

            try {
                saveTasks(tasks);
            } catch (IOException e) {
                System.out.println("Error loading tasks file: " + e.getMessage());
                System.out.println("The program cannot run properly without the file. Please fix this.");
            }
            if (printBoolean) {
                Task.printTasks();
            }
        }
    }
}
