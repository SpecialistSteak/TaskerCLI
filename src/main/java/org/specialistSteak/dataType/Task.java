package org.specialistSteak.dataType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import picocli.CommandLine;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.specialistSteak.dataType.AnsiColor.ansiEscape;
import static org.specialistSteak.dataType.Completed.returnCompletedStatus;
import static org.specialistSteak.dataType.Importance.importanceToString;
import static org.specialistSteak.dataType.UserData.lastUserData;
import static org.specialistSteak.dataType.UserData.loadLastUserData;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

public class Task {
    //instance variables
    public static ArrayList<Task> tasks = new ArrayList<>();
    private String description;
    private Importance priority;
    private Completed isCompleted;
    private String owner;
    private String lastEditedDate;
    private String addedDate;
    private AnsiColor ansiColor;
    private String dateCompleted;

    /**
     * Constructor for Task class. Contains data such as description, priority, and completion status.
     * Also contains other "metadata" such as owner, last edited date, and added date.
     *
     * @param description The description of the task.
     * @param priority    The priority of the task.
     * @param isCompleted If the task is completed.
     * @param ansiColor   The color of the task.
     * @throws IOException if the file is not found.
     */
    @JsonCreator
    public Task(@JsonProperty("description") String description, @JsonProperty("priority") Importance priority,
                @JsonProperty("getIsCompleted") Completed isCompleted, @JsonProperty("ansiColor") AnsiColor ansiColor) throws IOException {

        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.ansiColor = ansiColor;
        this.lastEditedDate = new Date().toString();
        this.addedDate = new Date().toString();
        this.isCompleted = isCompleted;
        loadLastUserData();
        this.owner = lastUserData.getUsername();
        if (Completed.returnCompletedStatus(isCompleted)) {
            this.dateCompleted = new Date().toString();
        } else {
            this.dateCompleted = null;
        }
    }

    /**
     * Set the added date of the task.
     *
     * @param addedDate the date the task was added.
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * Update the last edited date of the task.
     */
    public void updateLastEditedDate() {
        this.lastEditedDate = new Date().toString();
    }

    /**
     * Set the date the task last edited.
     *
     * @param lastEditedDate the last edited date of the task to set.
     */
    public void setLastEditedDate(String lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
    }

    /**
     * Set the owner of the task.
     *
     * @param owner the owner of the task to set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get the last edited date of the task.
     *
     * @return the last edited date of the task.
     */
    public String getLastEditedDate() {
        return lastEditedDate;
    }

    /**
     * Get the added date of the task.
     *
     * @return the added date of the task.
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     * Get the completion date of the task.
     *
     * @return the completion date of the task.
     */
    public String getDateCompleted() {
        return dateCompleted != null ? dateCompleted : null;
    }

    /**
     * Get the description of the task.
     *
     * @return the description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the priority of the task.
     *
     * @return the priority of the task.
     */
    public Importance getPriority() {
        return priority;
    }

    /**
     * Get the completion status of the task.
     *
     * @return the completion status of the task.
     */
    public Completed getIsCompleted() {
        return isCompleted;
    }

    /**
     * Get the owner of the task.
     *
     * @return the owner of the task.
     */
    public AnsiColor getAnsiColor() {
        return ansiColor;
    }

    /**
     * Get the owner of the task.
     *
     * @return the owner of the task.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the description of the task.
     *
     * @param dateCompleted the date the task was completed.
     */
    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    /**
     * Set the description of the task and update the last edited date.
     *
     * @param description the description of the task to set.
     */
    public void setDescription(String description) {
        this.description = description;
        this.lastEditedDate = new Date().toString();
    }

    /**
     * Set the priority of the task and update the last edited date.
     *
     * @param priority the priority of the task to set.
     */
    public void setPriority(Importance priority) {
        this.priority = priority;
        this.lastEditedDate = new Date().toString();
    }

    /**
     * Set the completion status of the task and update the date completed.
     *
     * @param isCompleted the completion status of the task to set.
     */
    public void setIsCompleted(Completed isCompleted) {
        this.isCompleted = isCompleted;
        if (returnCompletedStatus(isCompleted)) {
            this.dateCompleted = new Date().toString();
        }
        this.lastEditedDate = new Date().toString();
    }

    /**
     * Set the color of the task and update the last edited date.
     *
     * @param ansiColor the color of the task to set.
     */
    public void setAnsiColor(AnsiColor ansiColor) {
        this.ansiColor = ansiColor;
        this.lastEditedDate = new Date().toString();
    }

    /**
     * Get the task as a string. Note that this is not the same as the description, and is for testing purposes.
     *
     * @return the task as a string.
     */
    public String toString() {
        return "Description: " + description + " Priority: " + priority + " Completed: " + isCompleted + " Owner: " +
                owner + " Last Edited Date: " + lastEditedDate + " Added Date: " + addedDate + " Ansi Color: " +
                ansiColor + " Date Completed: " + dateCompleted;
    }

    /**
     * Prints all the tasks in a formatted way.
     *
     * @param tasks the tasks to print.
     */
    public static void printTasks(ArrayList<Task> tasks) {
        // Print the table header
        System.out.println("| Index | Description        | Priority     | Completed Status |");
        System.out.println("|-------|--------------------|--------------|------------------|");

        // Print the tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (!(tasks.get(i).getAnsiColor() == AnsiColor.DEFAULT)) {
                System.out.printf(
                        "| %-5d | %-18s | %-12s | %-16s |\n",
                        i,
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.getDescription())),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.getPriority() != null ?
                                importanceToString(task.getPriority()) : "None")),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.isCompleted != null ?
                                task.getIsCompleted().toString() : "Not Started"))
                );
            } else {
                System.out.printf(
                        "| %-5d | %-18s | %-10s | %-16s |\n",
                        i,
                        task.getDescription(),
                        task.getPriority() != null ? importanceToString(task.getPriority()) : "None",
                        task.getIsCompleted() != null ? task.getIsCompleted().toString() : "Not Started");
            }
        }
    }

    /**
     * Prints all the tasks in a formatted way, based off of the permanent ArrayList.
     */
    public static void printTasks() {
        // Print the table header
        System.out.println("| Index | Description        | Priority   | Completed Status |");
        System.out.println("|-------|--------------------|------------|------------------|");

        // Print the tasks
        for (int i = 0; i < Task.tasks.size(); i++) {
            Task task = Task.tasks.get(i);
            if (!(Task.tasks.get(i).getAnsiColor() == AnsiColor.DEFAULT)) {
                System.out.printf(
                        "| %-5d | %-18s | %-12s | %-16s |\n",
                        i,
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.getDescription())),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.getPriority() != null ?
                                importanceToString(task.getPriority()) : "None")),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.isCompleted != null ?
                                task.getIsCompleted().toString() : "Not Started"))
                );
            } else {
                System.out.printf(
                        "| %-5d | %-18s | %-10s | %-16s |\n",
                        i,
                        task.getDescription(),
                        task.getPriority() != null ? importanceToString(task.getPriority()) : "None",
                        task.getIsCompleted() != null ? task.getIsCompleted().toString() : "Not Started");
            }
        }
    }

    /**
     * Searches for a task based on the description.
     *
     * @param tasks      the tasks to search through.
     * @param searchTerm the term to search for.
     * @return the task/s that matches the search term.
     * @throws IOException if the file is not found.
     */
    public static ArrayList<Task> searchTasks(ArrayList<Task> tasks, String searchTerm) throws IOException {
        ArrayList<Task> tempTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(searchTerm)) {
                tempTasks.add(new Task(task.getDescription(), task.getPriority(), task.getIsCompleted(), task.getAnsiColor()));
            }
        }
        return tempTasks;
    }

    /**
     * Saves the tasks to a formatted json file.
     *
     * @param tasks the tasks to save to the file.
     * @throws IOException if the file is not found.
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        loadLastUserData();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(lastUserData.getTasklistAddress(), tasks);
        } catch (IOException ex) { //checks to see if the file works
            errorMessager(ex);
        }
    }

    /**
     * Loads the tasks from a formatted json file.
     *
     * @throws IOException if the file is not found.
     */
    public static void loadTasks() throws IOException {
        loadLastUserData();
        ObjectMapper mapper = new ObjectMapper();
        Task[] tasklist = new Task[0];
        File tasklistAddress = new File("");
        try {
            tasklistAddress = lastUserData.getTasklistAddress();
        } catch (NullPointerException ex) {
            errorMessager(ex);
        }
        try {
            tasklist = mapper.readValue(new FileReader(tasklistAddress), Task[].class);
        } catch (IOException ex) { //checks to see if the file works
            if (ex.getMessage().contains("Permission denied")) {
                System.out.println("You may need to use sudo privileges to edit the file.");
            }
            if (ex.getMessage().indexOf("find the file") > 0) {
                System.out.println("Error locating the file.");
                System.out.println("Attempting to generate a new file.");
                File file = new File(lastUserData.getTasklistAddress().toURI());
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created successfully.");
                    } else {
                        System.out.println("File already exists.");
                    }
                    mapper.writeValue(lastUserData.getTasklistAddress(), tasks);
                } catch (IOException e) {
                    errorMessager(e);
                }
            }
        }
        Task.tasks = new ArrayList<>(Arrays.asList(tasklist));
    }
}