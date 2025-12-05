package com.jminiapp.examples.schoolcontrol;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchoolControlApp extends JMiniApp {
    private Scanner scanner;
    private List<Student> students;
    private boolean running;

    public SchoolControlApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== School Control App ===");
        System.out.println("Welcome to the School Control App!");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing students from context
        List<Student> data = context.getData();
        if (data != null && !data.isEmpty()) {
            students = new ArrayList<>(data);
            System.out.println("Loaded " + students.size() + " students from file.");
        } else {
            students = new ArrayList<>();
            System.out.println("Starting with empty student list.");
        }
    }

    @Override
    protected void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        context.setData(students);
        scanner.close();
        System.out.println("\nFinal student list saved. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n--- School Control Menu ---");
        System.out.println("1. Add student");
        System.out.println("2. List students");
        System.out.println("3. Update grade");
        System.out.println("4. Export to JSON file");
        System.out.println("5. Import from JSON file");
        System.out.println("6. Exit");
        System.out.print("\nChoose an option: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    listStudents();
                    break;
                case "3":
                    updateGrade();
                    break;
                case "4":
                    exportToFile();
                    break;
                case "5":
                    importFromFile();
                    break;
                case "6":
                    running = false;
                    System.out.println("\nExiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter grade: ");
        int grade = Integer.parseInt(scanner.nextLine().trim());

        students.add(new Student(id, name, grade));
        System.out.println("Student added successfully!");
    }

    private void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private void updateGrade() {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter new grade: ");
        int grade = Integer.parseInt(scanner.nextLine().trim());

        students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        s -> {
                            s.setGrade(grade);
                            System.out.println("Grade updated successfully!");
                        },
                        () -> System.out.println("Student not found.")
                );
    }

    private void exportToFile() {
        try {
            context.setData(students);
            context.exportData("json");
            System.out.println("Student list exported successfully to: SchoolControl.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");
            List<Student> data = context.getData();
            if (data != null && !data.isEmpty()) {
                students = new ArrayList<>(data);
                System.out.println("Student list imported successfully from SchoolControl.json!");
            } else {
                System.out.println("Error: No data found in file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }
}