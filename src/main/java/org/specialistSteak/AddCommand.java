package org.specialistSteak;

import picocli.CommandLine;

import java.io.IOException;

import static org.specialistSteak.Task.saveTasks;
import static org.specialistSteak.Task.loadTasks;
import static org.specialistSteak.Task.tasks;

@CommandLine.Command(
        name = "add",
        mixinStandardHelpOptions = true
)
public class AddCommand implements Runnable {
    @CommandLine.Option(names = {"-p", "--priority"}, description = "Set new custom priority and exit.")
    private Integer priorityInteger;

    @CommandLine.Option(names = {"-c", "--complete"}, description = "Set new task to complete and exit.")
    private boolean completeBoolean;

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
            tasks.add(new Task(taskDescriptionString, (priorityInteger != null) ? priorityInteger : 0, completeBoolean, ansicolor));
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