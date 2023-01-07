package org.specialistSteak;

import java.io.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.Arrays;

public class Task {
    //instance variables
    public static ArrayList<Task> tasks = new ArrayList<>();
    private String description;
    private int priority;
    private boolean isCompleted;

    //constructor
    @JsonCreator
    public Task(@JsonProperty("description") String description, @JsonProperty("priority") int priority, @JsonProperty("isCompleted") boolean isCompleted) {
        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
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

    //setter methods
    public void setDescription(String description){
        this.description = description;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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
            System.out.printf("| %-5d | %-18s | %-8d | %-9s |\n", i, task.getDescription(), task.getPriority(), task.isCompleted() ? "Yes" : "No");
        }
    }

    //print tasks based off of 'permanent' array
    public static void printTasks(){
        // Print the table header
        System.out.println("| Index | Description        | Priority | Completed |");
        System.out.println("|-------|--------------------|----------|-----------|");

        // Print the tasks
        for (int i = 0; i < Task.tasks.size(); i++) {
            Task task = Task.tasks.get(i);
            System.out.printf("| %-5d | %-18s | %-8d | %-9s |\n", i, task.getDescription(), task.getPriority(), task.isCompleted() ? "Yes" : "No");
        }
    }

    //search method to search tasks
    public static ArrayList<Task> searchTasks(ArrayList<Task> tasks, String searchTerm) {
        ArrayList<Task> tempTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(searchTerm)) {
                tempTasks.add(new Task(task.getDescription(), task.getPriority(), task.isCompleted()));
            }
        }
        return tempTasks;
    }

    public static void saveTasks(ArrayList<Task> tasks) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("tasks.json"), tasks);
        } catch (IOException e) {
            System.out.println("Error saving/loading tasks file: " + e.getMessage());
            System.out.println("The program cannot run properly without the file.");
            if (e.getMessage().indexOf("Permission denied") > 0){
                System.out.println("You may need to use sudo privileges to edit the file.");
            }
        }
    }

    public static void loadTasks() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Task[] tasklist = mapper.readValue(new FileReader("tasks.json"), Task[].class);
        Task.tasks = new ArrayList<>(Arrays.asList(tasklist));
    }

    //Made to l
    @Deprecated
    public static void saveTasksToTxtFile(ArrayList<Task> tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.json"));
        for (Task task : tasks) {
            String line = String.format("%s|%d|%s", task.getDescription(), task.getPriority(), task.isCompleted());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    @Deprecated
    public static void loadTasksFromTxtFile() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|"); //split at | in file
            String description = parts[0];
            int priority = Integer.parseInt(parts[1]);
            boolean isCompleted = Boolean.parseBoolean(parts[2]);
            tasks.add(new Task(description, priority, isCompleted));
        }
        reader.close();

        Task.tasks = tasks;
    }
}