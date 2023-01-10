package org.specialistSteak;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import picocli.CommandLine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.specialistSteak.AnsiColor.ansiEscape;

//TODO:
// - Make it so that a user can have multiple taskboard objects (aka multiple files each with their own tasks)
public class Task {
    //instance variables
    public static ArrayList<Task> tasks = new ArrayList<>();
    private String description;
    private int priority;
    private boolean isCompleted;

    private String lastEditedDate;
    private String addedDate;
    private AnsiColor ansiColor;
    //constructor
    @JsonCreator
    public Task(@JsonProperty("description") String description, @JsonProperty("priority") int priority, @JsonProperty("isCompleted") boolean isCompleted, @JsonProperty("ansiColor") AnsiColor ansiColor) {
        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.ansiColor = ansiColor;
        this.lastEditedDate = new Date().toString();
        this.addedDate = new Date().toString();
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public void updateLastEditedDate(){
        this.lastEditedDate = new Date().toString();
    }

    public String getLastEditedDate() {
        return lastEditedDate;
    }
    public String getAddedDate() {
        return addedDate;
    }

    //getter methods
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public AnsiColor getAnsiColor() {
        return ansiColor;
    }

    //setter methods
    public void setDescription(String description){
        this.description = description;
        this.lastEditedDate = new Date().toString();
    }
    public void setPriority(int priority) {
        this.priority = priority;
        this.lastEditedDate = new Date().toString();
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        this.lastEditedDate = new Date().toString();
    }
    public void setAnsiColor(AnsiColor ansiColor) {
        this.ansiColor = ansiColor;
        this.lastEditedDate = new Date().toString();
    }

    //toString method for testing
    public String toString() {
        return description + " (priority: " + priority + ") " + isCompleted;
    }

    //print tasks given task array
    public static void printTasks(ArrayList<Task> tasks){
        // Print the table header
        System.out.println("| Index | Description        | Priority | Completed |");
        System.out.println("|-------|--------------------|----------|-----------|");

        // Print the tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if(!(tasks.get(i).getAnsiColor() == AnsiColor.DEFAULT)){
                System.out.printf(
                        "| %-5d | %-18s | %-8s | %-9s |\n",
                        i,
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task , task.getDescription())),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.getPriority())),
                        CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.isCompleted() ? "Yes" : "No"))
                );
            } else {
                System.out.printf("| %-5d | %-18s | %-8d | %-9s |\n", i, task.getDescription(), task.getPriority(), task.isCompleted() ? "Yes" : "No");
            }
        }
    }

    //print tasks based off of 'permanent' ArrayList
    public static void printTasks(){
        // Print the table header
        System.out.println("| Index | Description        | Priority | Completed |");
        System.out.println("|-------|--------------------|----------|-----------|");

        // Print the tasks
        for (int i = 0; i < Task.tasks.size(); i++) {
            Task task = Task.tasks.get(i);
            if(!(Task.tasks.get(i).getAnsiColor() == AnsiColor.DEFAULT)){
                System.out.printf(
                    "| %-5d | %-18s | %-8s | %-9s |\n",
                    i,
                    CommandLine.Help.Ansi.AUTO.string(ansiEscape(task , task.getDescription())),
                    CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.getPriority())),
                    CommandLine.Help.Ansi.AUTO.string(ansiEscape(task, task.isCompleted() ? "Yes" : "No"))
                );
            } else {
                System.out.printf("| %-5d | %-18s | %-8d | %-9s |\n", i, task.getDescription(), task.getPriority(), task.isCompleted() ? "Yes" : "No");
            }
        }
    }

    //search method to search tasks
    public static ArrayList<Task> searchTasks(ArrayList<Task> tasks, String searchTerm) {
        ArrayList<Task> tempTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(searchTerm)) {
                tempTasks.add(new Task(task.getDescription(), task.getPriority(), task.isCompleted(), task.getAnsiColor()));
            }
        }
        return tempTasks;
    }

    //makes json file and 'maps' tasks to it
    public static void saveTasks(ArrayList<Task> tasks) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("src/main/resources/tasks.json"), tasks);
        } catch (IOException e) { //checks to see if the file works
            System.out.println("Error saving/loading tasks file: " + e.getMessage());
            System.out.println("The program cannot run properly without the file.");
            if (e.getMessage().indexOf("Permission denied") > 0){
                System.out.println("You may need to use sudo privileges to edit the file.");
            }
        }
    }

    //loads file and sets tasks to proper values
    public static void loadTasks() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Task[] tasklist = mapper.readValue(new FileReader("src/main/resources/tasks.json"), Task[].class);
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
            int priority = Integer.parseInt(parts[1]);
            boolean isCompleted = Boolean.parseBoolean(parts[2]);
            AnsiColor ansiColor = AnsiColor.valueOf(parts[3]);
            tasks.add(new Task(description, priority, isCompleted, ansiColor));
        }
        reader.close();

        Task.tasks = tasks;
    }
}