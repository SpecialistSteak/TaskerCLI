package org.specialistSteak;

import org.specialistSteak.dataType.AnsiColor;
import org.specialistSteak.dataType.Completed;
import org.specialistSteak.dataType.Importance;
import org.specialistSteak.dataType.Task;
import picocli.CommandLine;

import java.io.IOException;

import static org.specialistSteak.dataType.Completed.completed;
import static org.specialistSteak.dataType.Completed.notStarted;
import static org.specialistSteak.dataType.Importance.low;
import static org.specialistSteak.dataType.Task.*;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

@CommandLine.Command(
        name = "add",
        description = "Has options related to creating new tasks.",
        mixinStandardHelpOptions = true
)
public class AddCommand implements Runnable {
    @CommandLine.Option(names = {"-p", "--priority"}, description = "Set new custom priority and exit. Supported values: LOW, MEDIUM, HIGH", defaultValue = "LOW")
    private Importance priorityImportance;

    @CommandLine.Option(names = {"-c", "--complete"}, description = "Set new task as complete and exit.")
    private boolean completedBoolean;

    @CommandLine.Option(names = {"-cs", "--completestatus"}, description = "Set new task's completion status and exit. Supported values: completed, notStarted, inProgress, waitingOnOthers, deferred", defaultValue = "notStarted")
    private Completed completeCompleted;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print list of tasks and exit.")
    private boolean printBoolean;

    @CommandLine.Option(names = {"-a", "--ansi"}, description = "Set new custom ansi color and exit. Supported values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK", defaultValue = "DEFAULT")
    private AnsiColor ansiColor;

    @CommandLine.Parameters
    private String taskDescriptionString;

    private Completed complete() {
        if (completedBoolean) return completed;
        if (completeCompleted != null) return completeCompleted;
        return notStarted;
    }

    @Override
    public void run() {
        //try loading tasks, make file if it fails, if that fails, let user know
        try {
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
            if (ansiColor == null) {
                ansiColor = AnsiColor.DEFAULT;
            }
            //add a new task with the user input as the task
            try {
                tasks.add(new Task(taskDescriptionString,
                        (priorityImportance != null) ? priorityImportance : low,
                        completeCompleted != null ? completeCompleted : notStarted,
                        ansiColor));
            } catch (IOException e) {
                errorMessager(e);
                System.exit(1);
            }
            System.out.println("Task added successfully.");

            //Try to save to file, catch error
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
        } catch (Exception e) {
            errorMessager(e);
            System.exit(70);
        }
    }
}