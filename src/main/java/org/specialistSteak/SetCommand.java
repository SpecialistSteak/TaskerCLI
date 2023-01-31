package org.specialistSteak;

import org.specialistSteak.dataType.AnsiColor;
import org.specialistSteak.dataType.Completed;
import org.specialistSteak.dataType.Importance;
import org.specialistSteak.dataType.Task;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Date;

import static org.specialistSteak.dataType.Completed.*;
import static org.specialistSteak.dataType.Task.*;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

//TODO: URGENT setting a task as complete or incomplete IS NOT WORKING

@CommandLine.Command(name = "set", description = "Has options related to setting new task values.", mixinStandardHelpOptions = true)
public class SetCommand implements Runnable {

    @CommandLine.Option(names = {"-d", "--description"}, description = "Set custom description and exit.")
    private String descriptionString;
    @CommandLine.Option(names = {"-a", "--ansi"}, description = "Set new custom ansi color and exit. Supported values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK")
    private AnsiColor ansiColor;
    @CommandLine.Option(names = {"-p", "--priority"}, description = "Set custom priority and exit.")
    private Importance priorityImportance;
    @CommandLine.Option(names = {"-c", "--complete"}, description = "Set task to complete and exit.")
    private Boolean completeBoolean;
    @CommandLine.Option(names = {"-cs", "--completedstatus"}, description = "Set task's completed status and exit.")
    private Completed completedStatusCompleted;
    @CommandLine.Option(names = {"-PA", "--priorityall"}, description = "Set priority of all tasks and exit.")
    private Importance priorityAllImportance;
    @CommandLine.Option(names = {"-CA", "--completeall"}, description = "Set all tasks to complete and exit.")
    private boolean completeAllBoolean;
    @CommandLine.Option(names = {"-IA", "--incompleteall"}, description = "Set all tasks to incomplete and exit.")
    private boolean incompleteAllBoolean;
    @CommandLine.Option(names = {"-aa", "--ansiall"}, description = "Set all tasks to ansi color and exit. Supported values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK")
    private AnsiColor ansiAllColor;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print list of tasks and exit.")
    private boolean printBoolean;

    @CommandLine.Parameters
    private int setIndex;

    @Override
    public void run() {
        try {
            try {
                loadTasks();
            } catch (IOException e) {
                try {
                    saveTasks(tasks);
                } catch (IOException ex) {
                    errorMessager(ex);
                }
            }
            //try loading tasks, make file if it fails, if that fails, let user know

            //prelim check
            if (tasks.size() == 0) {
                System.out.println("Try adding some tasks first.");
            } else {
                //concise way to check what has been used in command and set it if it's used
                if (descriptionString != null || priorityImportance != null || ansiColor != null) {
                    Task task = tasks.get(setIndex);
                    task.setDescription(descriptionString != null ? descriptionString : task.getDescription());
                    task.setPriority(priorityImportance != null ? priorityImportance : task.getPriority());
                    if (completedStatusCompleted != null) {
                        task.setIsCompleted(completedStatusCompleted);
                    } else if (completeBoolean != null) {
                        task.setIsCompleted(completed);
                    } else {
                        task.setIsCompleted(task.getIsCompleted());
                    }
                    task.setAnsiColor(ansiColor != null ? ansiColor : task.getAnsiColor());
                    if (returnCompletedStatus(task.getIsCompleted())) {
                        task.setDateCompleted(new Date().toString());
                    }

                    tasks.set(setIndex, task);
                    System.out.printf("Task at index %d has been set to: | '%s' | %s | %s | %s |\n", setIndex, task.getDescription(), task.getPriority(), task.getIsCompleted(), task.getAnsiColor());
                }
            }

            //loop and set all priorities
            if (priorityAllImportance != null) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    tempTask.setPriority(priorityAllImportance);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to a priority of " + priorityAllImportance + ".");
            }

            if (ansiAllColor != null) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    //set method updates field and sets edit date
                    tempTask.setAnsiColor(ansiAllColor);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to an ansi color of " + ansiAllColor + ".");
            }

            //loop and set all incomplete
            if (incompleteAllBoolean) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    //set method updates field and sets edit date
                    tempTask.setIsCompleted(inProgress);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to incomplete.");
            }

            //loop and set all as complete
            if (completeAllBoolean) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    //set method updates field and sets edit date
                    tempTask.setIsCompleted(completed);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to complete.");
            }

            //try to save to file
            try {
                saveTasks(tasks);
            } catch (IOException e) {
                errorMessager(e);
                System.exit(1);
            }

            //if print is used, print tasks
            if (printBoolean) {
                Task.printTasks();
            }
        } catch (Exception e) {
            errorMessager(e);
            System.exit(70);
        }
    }
}