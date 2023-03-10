package org.specialistSteak;

import org.specialistSteak.dataType.Task;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;

import static org.specialistSteak.dataType.Completed.returnCompletedStatus;
import static org.specialistSteak.dataType.Task.*;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

@CommandLine.Command(
        name = "delete",
        description = "Has options related to deleting tasks.",
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
            //try loading tasks, make file if it fails, if that fails, let user know
            try {
                loadTasks();
            } catch (IOException e) {
                try {
                    saveTasks(tasks);
                } catch (IOException ex) {
                    errorMessager(ex);
                    System.exit(1);
                }
            }
            //Prelim check
            if (tasks.size() == 0) {
                System.out.println("Try adding some tasks first.");
            } else {
                //Delete searched tasks if search is used
                if (searchTermString != null) {
                    try {
                        tasks.removeAll(searchTasks(tasks, searchTermString));
                    } catch (IOException e) {
                        errorMessager(e);
                        System.exit(1);
                    }
                    System.out.println("All tasks containing the search term have been deleted.");
                }
                //delete task at index if index is used
                if (indexInteger != null) {
                    tasks.remove(indexInteger.intValue());
                    System.out.printf("Task at index %d has been deleted\n", indexInteger);
                }
                //delete all completed tasks if completed bool is used
                if (completedBoolean) {
                    ArrayList<Task> toRemove = new ArrayList<>();
                    for (Task task : tasks) {
                        if (returnCompletedStatus(task.getIsCompleted())) {
                            toRemove.add(task);
                        }
                    }
                    tasks.removeAll(toRemove);
                    System.out.println("All complete tasks successfully deleted.");
                }
                //delete all incompleted tasks if completed bool is used
                if (incompletedBoolean) {
                    ArrayList<Task> toRemove = new ArrayList<>();
                    for (Task task : tasks) {
                        if (!returnCompletedStatus(task.getIsCompleted())) {
                            toRemove.add(task);
                        }
                    }
                    tasks.removeAll(toRemove);
                    System.out.println("All incomplete tasks successfully deleted.");
                }
                //Remove all tasks if all option is used
                if (allBoolean) {
                    tasks.clear();
                    System.out.println("All tasks successfully deleted.");
                }

                //Try to save tasks, catch error
                try {
                    saveTasks(tasks);
                } catch (IOException e) {
                    errorMessager(e);
                    System.exit(1);
                }
                //print if option is used
                if (printBoolean) {
                    Task.printTasks();
                }
            }
        } catch (Exception e) {
            errorMessager(e);
            System.exit(70);
        }
    }
}