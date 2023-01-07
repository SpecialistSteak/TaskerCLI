package org.specialistSteak;

import picocli.CommandLine;

import java.io.IOException;

import static org.specialistSteak.Task.*;

@CommandLine.Command(
        name = "set",
        mixinStandardHelpOptions = true
)
public class SetCommand implements Runnable {
    @CommandLine.Option(names = {"-d", "--description"}, description = "Set custom description and exit.")
    private String descriptionString;

    @CommandLine.Option(names = {"-p", "--priority"}, description = "Set custom priority and exit.")
    private Integer priorityInteger;

    @CommandLine.Option(names = {"-c", "--complete"}, description = "Set task to complete and exit.")
    private Boolean completeBoolean;

    @CommandLine.Option(names = {"-PA", "--priorityall"}, description = "Set priority of all tasks and exit.")
    private Integer priorityAllInteger;

    @CommandLine.Option(names = {"-CA", "--completeall"}, description = "Set all tasks to complete and exit.")
    private boolean completeAllBoolean;

    @CommandLine.Option(names = {"-IA", "--incompleteall"}, description = "Set all tasks to incomplete and exit.")
    private boolean incompleteAllBoolean;

    @CommandLine.Option(names = {"-P", "--print"}, description = "Print list of tasks and exit.")
    private boolean printBoolean;

    @CommandLine.Parameters
    private int setIndex;

    @Override
    public void run() {
        try {
            loadTasks();
        }
        catch (IOException e){
            saveTasks(tasks);
        }
        //try loading tasks, make file if it fails, if that fails, let user know

        //prelim check
        if(tasks.size() == 0){
            System.out.println("Try adding some tasks first.");
        }
        else {
            //concise way to check what has been used in command and set it if it's used
            if (descriptionString != null || priorityInteger != null || completeBoolean != null) {
                tasks.set(setIndex, new Task(
                        //if below is NOT null      ? set to field      : otherwise keep it the same
                        (descriptionString != null) ? descriptionString : tasks.get(setIndex).getDescription(),
                        (priorityInteger   != null) ? priorityInteger   : tasks.get(setIndex).getPriority(),
                        (completeBoolean   != null) ? completeBoolean   : tasks.get(setIndex).isCompleted()));
                System.out.printf("Task at index %d has been set to '%s', %d, %b\n", setIndex, (descriptionString != null) ? descriptionString : tasks.get(setIndex).getDescription(),
                        (priorityInteger != null) ? priorityInteger : tasks.get(setIndex).getPriority(),
                        (completeBoolean != null) ? (completeBoolean ? "Yes" : "No") : (tasks.get(setIndex).isCompleted() ? "Yes" : "No"));
            }

            //loop and set all priorities
            if (priorityAllInteger != null) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    tempTask.setPriority(priorityAllInteger);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to a priority of " + priorityAllInteger + ".");
            }

            //loop and set all incomplete
            if (incompleteAllBoolean) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    tempTask.setCompleted(false);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to incomplete.");
            }

            //loop and set all complete
            if (completeAllBoolean) {
                for (int i = 0; i < tasks.size(); i++) {
                    Task tempTask = tasks.get(i);
                    tempTask.setCompleted(true);
                    tasks.set(i, tempTask);
                }
                System.out.println("All tasks have been set to complete.");
            }

            //try to save to file, catch error
            saveTasks(tasks);

            //if print is used, print tasks
            if (printBoolean) {
                Task.printTasks();
            }
        }
    }
}