package com.jminiapp.examples.studyplanner;
import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based study planner application built with jminiapp
 */
public class StudyPlannerApp extends JMiniApp {

    private Scanner scanner;
    private List<StudyTask> tasks;
    private boolean running;
    private int nextId;

    public StudyPlannerApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Study Planner App ===");
            System.out.println("Welcome to the Study Planner!");

        scanner = new Scanner(System.in);
        running = true;

        List<StudyTask> data = context.getData();
        if (data != null && !data.isEmpty()) {
            tasks = new ArrayList<>(data);
                nextId = tasks.stream()
                    .mapToInt(StudyTask::getId)
                    .max()
                    .orElse(0) + 1;
            System.out.println("Loaded " + tasks.size() + " study tasks from previous session.");
        } else {
            tasks = new ArrayList<>();
                nextId = 1;
                    System.out.println("Starting with an empty study plan.");
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
        context.setData(tasks);
        scanner.close();
        System.out.println("\nSaving " + tasks.size() + " study tasks...");
        System.out.println("Goodbye and good luck with your exams!");
    }

    private void displayMenu() {
        System.out.println("\n--- Study Planner ---");
        if (tasks.isEmpty()) {
            System.out.println("No study tasks yet.");
        } else {
            for (StudyTask task : tasks) {
                System.out.println(" - " + task);
            }
        }

        System.out.println("\nOptions:");
        System.out.println("1. Add study task");
        System.out.println("2. Change task status");
        System.out.println("3. List tasks by subject");
        System.out.println("4. Show total estimated study time");
        System.out.println("5. Export planner to JSON file");
        System.out.println("6. Import planner from JSON file");
        System.out.println("7. Exit");
        System.out.print("\nChoose an option (1-7): ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addTask();
                case "2" -> changeStatus();
                case "3" -> listBySubject();
                case "4" -> showTotalTime();
                case "5" -> exportToFile();
                case "6" -> importFromFile();
                case "7" -> {
                    running = false;
                    System.out.println("\nExiting...");
                }
                default -> System.out.println("Invalid option. Please choose 1-7.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addTask() {
        System.out.print("Subject: ");
        String subject = scanner.nextLine().trim();

        System.out.print("Topic: ");
        String topic = scanner.nextLine().trim();

        System.out.print("Deadline (YYYY-MM-DD): ");
        String deadline = scanner.nextLine().trim();

        System.out.print("Estimated study time in minutes: ");
        String timeInput = scanner.nextLine().trim();

        int minutes;
        try {
            minutes = Integer.parseInt(timeInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Task not created.");
            return;
        }

        StudyTask task = new StudyTask(nextId++, subject, topic, deadline, minutes);
        tasks.add(task);
        System.out.println("Study task added: " + task);
    }

    private void changeStatus() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to update.");
            return;
        }

        System.out.print("Enter task id to update: ");
        String input = scanner.nextLine().trim();

        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
            return;
        }

        StudyTask task = findById(id);
        if (task == null) {
            System.out.println("Task with id " + id + " not found.");
            return;
        }

        System.out.print("New status (pending, in-progress, done): ");
        String status = scanner.nextLine().trim().toLowerCase();

        if (!status.equals("pending") && !status.equals("in-progress") && !status.equals("done")) {
            System.out.println("Invalid status value.");
            return;
        }

        task.setStatus(status);
        System.out.println("Task updated: " + task);
    }

    private void listBySubject() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to filter.");
            return;
        }

        System.out.print("Enter subject to filter: ");
        String subject = scanner.nextLine().trim();

        boolean found = false;
        for (StudyTask task : tasks) {
            if (task.getSubject().equalsIgnoreCase(subject)) {
                if (!found) {
                    System.out.println("\nTasks for subject: " + subject);
                    found = true;
                }
                System.out.println(" - " + task);
            }
        }

        if (!found) {
            System.out.println("No tasks found for subject: " + subject);
        }
    }

    private void showTotalTime() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to calculate.");
            return;
        }

        int totalMinutes = 0;
        for (StudyTask task : tasks) {
            totalMinutes += task.getEstimatedMinutes();
        }

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        System.out.println("Total estimated study time: "
                + totalMinutes + " minutes (~" + hours + "h " + minutes + "min)");
    }

    private StudyTask findById(int id) {
        for (StudyTask task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    private void exportToFile() {
        try {
            context.setData(tasks);
            context.exportData("json");
            System.out.println("Study planner exported successfully to: StudyPlanner.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");
            List<StudyTask> data = context.getData();
            if (data != null && !data.isEmpty()) {
                tasks = new ArrayList<>(data);
                nextId = tasks.stream()
                        .mapToInt(StudyTask::getId)
                        .max()
                        .orElse(0) + 1;
                System.out.println("Study planner imported successfully from StudyPlanner.json!");
                System.out.println("Loaded " + tasks.size() + " tasks.");
            } else {
                System.out.println("Error: no data found in file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }
}
