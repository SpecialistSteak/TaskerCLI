package org.specialistSteak;

import java.io.*;
import java.util.ArrayList;
public class Task {
    public static ArrayList<Task> tasks = new ArrayList<Task>();
    private String description;
    private int priority;
    private boolean isCompleted;

    public Task(String description, int priority, boolean isCompleted) {
        this.description = description;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String toString() {
        return description + " (priority: " + priority + ")";
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

    public static ArrayList<Task> searchTasks(ArrayList<Task> tasks, String searchTerm) {
        ArrayList<Task> tempTasks = new ArrayList<>();

        // Iterate through the tasks and search for a matching description
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().contains(searchTerm)) {
                tasks.add(new Task(task.getDescription(), task.getPriority(), task.isCompleted()));
            }
        }

        return tempTasks;
    }
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"));for (Task task : tasks) {
            String line = String.format("%s|%d|%s", task.getDescription(), task.getPriority(), task.isCompleted());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
    public static void loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();

        BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            String description = parts[0];
            int priority = Integer.parseInt(parts[1]);
            boolean isCompleted = Boolean.parseBoolean(parts[2]);
            tasks.add(new Task(description, priority, isCompleted));
        }
        reader.close();

        Task.tasks = tasks;
    }
}