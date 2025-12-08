    package com.jminiapp.examples.notetaking;

import com.jminiapp.core.api.*;
import java.util.*;

/**
 * A simple note-taking application demonstrating the JMiniApp framework.
 *
 * This app allows users to:
 * - Edit the current note
 * - Delete the current note
 * - View the current note
 * - Save the note and exit
 */

public class NotetakingApp extends JMiniApp {
    private Scanner scanner;
    private NotetakingState note;
    private boolean running;

    public NotetakingApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Note Taking App ===");
        System.out.println("Welcome to the Note Taking App!");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing note state from context
        List<NotetakingState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            note = data.get(0);
            System.out.println("Loaded existing note: " + note.getTitle());
            System.out.println(note.getContent());
        } else {
            note = new NotetakingState("", "");
            System.out.println("Starting with a new note.");
        }
    }

    @Override
    protected void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    private void displayMenu() {
        System.out.println("\nCurrent Note:");
        System.out.println("Title: " + note.getTitle());
        System.out.println("Content: " + note.getContent());
        System.out.println("\nMenu:");
        System.out.println("1. Edit Note");
        System.out.println("2. Delete Note");
        System.out.println("3. Export to JSON file");
        System.out.println("4. Import from JSON file");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleUserInput() {
        try{
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.print("Enter new title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter new content: ");
                    String content = scanner.nextLine();
                    note.setTitle(title);
                    note.setContent(content);
                    System.out.println("Note updated.");
                    break;
                case "2":
                    note.clear();
                    System.out.println("Note deleted.");
                    break;   
                case "3":
                    exportToFile();
                    break;
                case "4":
                    importFromFile();
                    break;
                case "5":
                    running = false;
                    System.out.println("\nExiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void shutdown() {
        // Save the note state to context
        List<NotetakingState> data = new ArrayList<>(List.of(note));
        context.setData(data);

        scanner.close();
        System.out.println("\nFinal note saved: " + note.getTitle());
        System.out.println(note.getContent());
        System.out.println("Goodbye!");
    }

    private void exportToFile() {
        try {
            context.setData(new ArrayList<>(List.of(note)));

            context.exportData("json");
            System.out.println("Note exported successfully.");
        } catch (Exception e) {
            System.out.println("Failed to export note: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");

            List<NotetakingState> data = context.getData();
            if (data != null && !data.isEmpty()) {
                note = data.get(0);
                System.out.println("Note imported successfully.");
            } else {
                System.out.println("No note data found.");
            }
        } catch (Exception e) {
            System.out.println("Failed to import note: " + e.getMessage());
            e.printStackTrace();
        }
    }
}