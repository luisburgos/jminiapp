package com.example.todolist;

public class Task {
    private int id;
    private String description;
    private boolean completed;

    // Default constructor for JSON deserialization
    public Task() {}

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s", id, description, completed ? "Completed" : "Pending");
    }
}