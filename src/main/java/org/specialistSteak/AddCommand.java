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

    @CommandLine.Parameters
    private String taskDescriptionString;

    @Override
    public void run() {
        //try loading tasks, make file if it fails, if that fails, let user know
        try {
            loadTasks();
        }
        catch (IOException e){
            try {
                saveTasks(tasks);
            } catch (IOException ex) {
                System.out.println("Error loading tasks file: " + e.getMessage());
                System.out.println("The program cannot run properly without the file. Please fix this.");
                System.out.println((e.getMessage().indexOf("Permission denied")>0) ? "You may need to use sudo privileges to edit the file." : "");
            }
        }
        //add a new task with the user input as the task
        tasks.add(new Task(taskDescriptionString, (priorityInteger != null) ? priorityInteger : 0, completeBoolean));
        System.out.println("Task added successfully.");

        //Try to save to file, catch error
        try {
            saveTasks(tasks);
        }
        catch (IOException e) {
            System.out.println("Error loading tasks file: " + e.getMessage());
            System.out.println("The program cannot run properly without the file. Please fix this.");
        }
        //print if option is used
        if(printBoolean){
            Task.printTasks();
        }
    }
}
