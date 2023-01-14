package org.specialistSteak.dataType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import picocli.CommandLine;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.specialistSteak.dataType.UserData.lastUserData;
import static org.specialistSteak.dataType.UserData.loadLastUserData;
import static org.specialistSteak.dataType.AnsiColor.ansiEscape;
import static org.specialistSteak.dataType.Completed.returnCompletedStatus;
import static org.specialistSteak.dataType.Importance.importanceToString;

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
    //constructor
    @JsonCreator
    public Task(@JsonProperty("description") String description, @JsonProperty("priority") Importance priority,
                @JsonProperty("isCompleted") Completed isCompleted, @JsonProperty("ansiColor") AnsiColor ansiColor) throws IOException {

        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.ansiColor = ansiColor;
        this.lastEditedDate = new Date().toString();
        this.addedDate = new Date().toString();

        loadLastUserData();
        this.owner = lastUserData.getUsername();
        if(Completed.returnCompletedStatus(isCompleted)) {
            this.dateCompleted = new Date().toString();
        } else {
            this.dateCompleted = null;
        }
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public void updateLastEditedDate(){
        this.lastEditedDate = new Date().toString();
    }

    public void setLastEditedDate(String lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getLastEditedDate() {
        return lastEditedDate;
    }
    public String getAddedDate() {
        return addedDate;
    }
    public String getDateCompleted() {
        return dateCompleted != null ? dateCompleted : null;
    }

    //getter methods
    public String getDescription() {
        return description;
    }
    public Importance getPriority() {
        return priority;
    }
    public Completed isCompleted() {
        return isCompleted;
    }
    public AnsiColor getAnsiColor() {
        return ansiColor;
    }
    public String getOwner() {
        return owner;
    }
    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    //setter methods
    public void setDescription(String description){
        this.description = description;
        this.lastEditedDate = new Date().toString();
    }
    public void setPriority(Importance priority) {
        this.priority = priority;
        this.lastEditedDate = new Date().toString();
    }
    public void setCompleted(Completed isCompleted) {
        this.isCompleted = isCompleted;
        if(returnCompletedStatus(isCompleted)) {
            this.dateCompleted = new Date().toString();
        }
        this.lastEditedDate = new Date().toString();
    }
    public void setAnsiColor(AnsiColor ansiColor) {
        this.ansiColor = ansiColor;
        this.lastEditedDate = new Date().toString();
    }

    //toString method for testing
    public String toString() {
        return "Description: " + description + " Priority: " + priority + " Completed: " + isCompleted + " Owner: " +
                owner + " Last Edited Date: " + lastEditedDate + " Added Date: " + addedDate + " Ansi Color: " +
                ansiColor + " Date Completed: " + dateCompleted;
    }

    //print tasks given task array
    public static void printTasks(ArrayList<Task> tasks){
        // Print the table header
        System.out.println("| Index | Description        | Priority   | Completed Status |");
        System.out.println("|-------|--------------------|------------|------------------|");

        // Print the tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if(!(tasks.get(i).getAnsiColor() == AnsiColor.DEFAULT)){
                System.out.printf(
                        "| %-5d | %-18s | %-8s | %-16s |\n",
                        i,
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task , task.getDescription())),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, importanceToString(task.getPriority()))),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.isCompleted().toString()))
                );
            } else {
                System.out.printf("| %-5d | %-18s | %-8s | %-16s |\n", i, task.getDescription(), importanceToString(task.getPriority()), task.isCompleted().toString());
            }
        }
    }

    //print tasks based off of 'permanent' ArrayList
    public static void printTasks(){
        // Print the table header
        System.out.println("| Index | Description        | Priority | Completed Status |");
        System.out.println("|-------|--------------------|----------|------------------|");

        // Print the tasks
        for (int i = 0; i < Task.tasks.size(); i++) {
            Task task = Task.tasks.get(i);
            if(!(Task.tasks.get(i).getAnsiColor() == AnsiColor.DEFAULT)){
                System.out.printf(
                    "| %-5d | %-18s | %-8s | %-16s |\n",
                    i,
                    CommandLine.Help.Ansi.AUTO.string(ansiEscape(task , task.getDescription())),
                    CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, importanceToString(task.getPriority()))),
                    CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.isCompleted().toString()))
                );
            } else {
                System.out.printf("| %-5d | %-18s | %-8s | %-16s |\n", i, task.getDescription(), importanceToString(task.getPriority()), task.isCompleted().toString());
            }
        }
    }

    //search method to search tasks
    public static ArrayList<Task> searchTasks(ArrayList<Task> tasks, String searchTerm) throws IOException {
        ArrayList<Task> tempTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(searchTerm)) {
                tempTasks.add(new Task(task.getDescription(), task.getPriority(), task.isCompleted(), task.getAnsiColor()));
            }
        }
        return tempTasks;
    }

    //makes json file and 'maps' tasks to it
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        loadLastUserData();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(lastUserData.getTasklistAddress(), tasks);
        } catch (IOException ex) { //checks to see if the file works
            if (ex.getMessage().indexOf("Permission denied") > 0){
                System.out.println("You may need to use sudo privileges to edit the file.");
            }
            if(!ex.getMessage().contains("find the file")){
                ex.printStackTrace();
            }
        }
    }

    //loads file and sets tasks to proper values
    public static void loadTasks() throws IOException {
        loadLastUserData();
        ObjectMapper mapper = new ObjectMapper();
        Task[] tasklist = new Task[0];
        try {
            tasklist = mapper.readValue(new FileReader(lastUserData.getTasklistAddress()), Task[].class);
        } catch (IOException ex) { //checks to see if the file works
            if (ex.getMessage().indexOf("Permission denied") > 0){
                System.out.println("You may need to use sudo privileges to edit the file.");
            }
            if(!ex.getMessage().contains("find the file")){
                ex.printStackTrace();
            }
        }
        Task.tasks = new ArrayList<>(Arrays.asList(tasklist));
    }

    //changed to different save "system" in favor of json
    //Saves file in format String|int|boolean|AnsiColor
    @Deprecated
    public static void saveTasksToTxtFile(ArrayList<Task> tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/tasks.txt"));
        for (Task task : tasks) {
            String line = String.format("%s|%d|%s|%s", task.getDescription(), task.getPriority(), task.isCompleted(), task.getAnsiColor());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    //Rewrites changes in format String|int|boolean|AnsiColor
    @Deprecated
    public static void loadTasksFromTxtFile() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/tasks.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|"); //split at | in file
            String description = parts[0];
            Importance priority = Importance.valueOf(parts[1]);
            Completed isCompleted = Completed.valueOf(parts[2]);
            AnsiColor ansiColor = AnsiColor.valueOf(parts[3]);
            tasks.add(new Task(description, priority, isCompleted, ansiColor));
        }
        reader.close();
        Task.tasks = tasks;
    }
}