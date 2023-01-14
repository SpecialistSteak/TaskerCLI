package org.specialistSteak;

import org.specialistSteak.dataType.AnsiColor;
import org.specialistSteak.dataType.Completed;
import org.specialistSteak.dataType.Importance;
import org.specialistSteak.dataType.Task;
import picocli.CommandLine;

import java.io.IOException;

import static org.specialistSteak.dataType.Completed.*;
import static org.specialistSteak.dataType.Importance.low;
import static org.specialistSteak.dataType.Task.saveTasks;
import static org.specialistSteak.dataType.Task.loadTasks;
import static org.specialistSteak.dataType.Task.tasks;

@CommandLine.Command(
        name = "add",
        mixinStandardHelpOptions = true
)
public class AddCommand implements Runnable {
    @CommandLine.Option(names = {"-p", "--priority"}, description = "Set new custom priority and exit.")
    private Importance priorityImportance;

    @CommandLine.Option(names = {"-c", "--complete"}, description = "Set new task as complete and exit.")
    private boolean completedBoolean;

    @CommandLine.Option(names = {"-cs", "--completestatus"}, description = "Set new task's completion status and exit.")
    private Completed completeCompleted;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print list of tasks and exit.")
    private boolean printBoolean;

    @CommandLine.Option(names = {"-a", "--ansi"}, description = "Set new custom ansi color and exit. Supported values: RED, YELLOW, GREEN, BLUE, WHITE, BLACK", defaultValue = "DEFAULT")
    private AnsiColor ansicolor;

    @CommandLine.Parameters
    private String taskDescriptionString;

    @Override
    public void run() {
        //try loading tasks, make file if it fails, if that fails, let user know
        try {
            loadTasks();
        } catch (IOException e) {
            try {
                saveTasks(tasks);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(ansicolor == null) {
            ansicolor = AnsiColor.DEFAULT;
        }
        //add a new task with the user input as the task
        try {
            tasks.add(new Task(taskDescriptionString,
                    (priorityImportance != null) ? priorityImportance : low,
                    (completeCompleted != null ? completeCompleted :
                            (completedBoolean ? completed : notStarted)
                    ),
                    ansicolor));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Task added successfully.");

        //Try to save to file, catch error
        try {
            saveTasks(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //print if option is used
        if (printBoolean) {
            Task.printTasks();
        }
    }
}