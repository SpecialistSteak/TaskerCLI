package org.specialistSteak;

import org.specialistSteak.dataType.Importance;
import org.specialistSteak.dataType.Task;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;

import static org.specialistSteak.dataType.Completed.returnCompletedStatus;
import static org.specialistSteak.dataType.Task.*;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

@CommandLine.Command(
        name = "print",
        description = "Has options related to printing tasks.",
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
                errorMessager(ex);
                System.exit(0);
            }
        }
        //try loading tasks, make file if it fails, if that fails, let user know

        ArrayList<Task> taskCopy = new ArrayList<>(tasks);
        //make an array for the copy of tasks (to allow for -P option)

        //if search was used, search tasks
        if (searchTermString != null) {
            try {
                taskCopy = searchTasks(taskCopy, searchTermString);
            } catch (IOException e) {
                errorMessager(e);
                System.exit(0);
            }
        }
        //filter out incomplete to temp List if used
        if (filterBoolean) {
            ArrayList<Task> toRemove = new ArrayList<>();
            for (Task task : taskCopy) {
                if (returnCompletedStatus(task.getIsCompleted())) {
                    toRemove.add(task);
                }
            }
            taskCopy.removeAll(toRemove);
        }
        //order to temp List if used

        if (orderBoolean) {
            for (int i = 0; i < taskCopy.size() - 1; i++) {
                for (int j = i + 1; j < taskCopy.size(); j++) {
                    if (Importance.importanceToInteger(taskCopy.get(i).getPriority()) > Importance.importanceToInteger(taskCopy.get(j).getPriority())) {
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
        try {
            saveTasks(tasks);
        } catch (IOException e) {
            errorMessager(e);
            System.exit(0);
        }
        printTasks(taskCopy);
    }
}
