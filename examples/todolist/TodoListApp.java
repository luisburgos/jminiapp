package com.example.todolist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoListApp extends JMiniApp {
    private List<Task> tasks;
    private int nextId = 1;

    public TodoListApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        List<Task> data = context.getData();
        tasks = data.isEmpty() ? new ArrayList<>() : new ArrayList<>(data);
        // Find next ID
        if (!tasks.isEmpty()) {
            nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        }
    }

    @Override
    protected void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Todo List App!");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) {
                break;
            } else if (input.equals("list")) {
                listTasks();
            } else if (input.startsWith("add ")) {
                addTask(input.substring(4));
            } else if (input.startsWith("complete ")) {
                completeTask(input.substring(9));
            } else if (input.startsWith("delete ")) {
                deleteTask(input.substring(7));
            } else {
                System.out.println("Unknown command. Use: add <desc>, list, complete <id>, delete <id>, exit");
            }
        }
        scanner.close();
    }

    @Override
    protected void shutdown() {
        context.setData(new ArrayList<>(tasks));
    }

    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private void addTask(String description) {
        if (description.isEmpty()) {
            System.out.println("Description cannot be empty.");
            return;
        }
        tasks.add(new Task(nextId++, description));
        System.out.println("Task added.");
    }

    private void completeTask(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            Task task = findTaskById(id);
            if (task != null) {
                task.setCompleted(true);
                System.out.println("Task marked as completed.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void deleteTask(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            Task task = findTaskById(id);
            if (task != null) {
                tasks.remove(task);
                System.out.println("Task deleted.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private Task findTaskById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public static void main(String[] args) {
        JMiniAppRunner.forApp(TodoListApp.class)
                .withState(Task.class)
                .run(args);
    }
}