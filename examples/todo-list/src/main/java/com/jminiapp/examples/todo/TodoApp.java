package com.jminiapp.examples.todo;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp extends JMiniApp {
    private List<TodoItem> tasks; // Stores the current list of tasks.
    private Scanner scanner;       // Used for reading user input from the console.
    private boolean isRunning;     // Flag to control the main application loop.

    public TodoApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("--- Initializing Todo List App ---");
        // Load existing data from the context.
        List<TodoItem> loadedData = context.getData();
        
        if (loadedData != null && !loadedData.isEmpty()) {
            // If data is found, use the loaded tasks.
            tasks = new ArrayList<>(loadedData);
            System.out.println("Loaded " + tasks.size() + " tasks.");
        } else {
            // If no data is found, start with an empty list.
            tasks = new ArrayList<>();
            System.out.println("No existing tasks found. Starting fresh.");
        }
        
        scanner = new Scanner(System.in);
        isRunning = true;
    }

    @Override
    protected void run() {
        // The main application loop where user interaction happens.
        while (isRunning) {
            printMenu();
            handleInput();
        }
    }

    @Override
    protected void shutdown() {
        System.out.println("Saving tasks...");
        // Save the current state back to the context before exiting.
        context.setData(tasks);
        scanner.close();
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n--- My Tasks ---");
        if (tasks.isEmpty()) {
            System.out.println("(No tasks yet)");
        } else {
            // Display each task with its index and status.
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println("----------------");
        System.out.println("A. Add Task");
        System.out.println("C. Complete Task");
        System.out.println("X. Exit");
        System.out.print("Select option: ");
    }

    private void handleInput() {
        // Reads and normalizes user input.
        String input = scanner.nextLine().trim().toUpperCase();
        
        switch (input) {
            case "A":
                // Handles adding a new task to the list.
                System.out.print("Enter task description: ");
                String desc = scanner.nextLine();
                tasks.add(new TodoItem(desc));
                break;
            case "C":
                // Handles marking an existing task as complete.
                System.out.print("Enter number to complete: ");
                try {
                    // Parses the task index.
                    int index = Integer.parseInt(scanner.nextLine()) - 1; 
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).setDone(true);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
                break;
            case "X":
                // Sets the running flag to false to exit the main loop.
                isRunning = false;
                break;
            default:
                System.out.println("Unknown option.");
        }
    }
}