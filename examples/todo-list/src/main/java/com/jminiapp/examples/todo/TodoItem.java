package com.jminiapp.examples.todo;

public class TodoItem {
    private String description; // The description of the task.
    private boolean isDone;      // Status: true if completed, false otherwise.

    // Empty constructor required for the framework's data loading.
    public TodoItem() {}

    /**
     * Initializes a new task with a description.
     * @param description The text description of the task.
     */
    public TodoItem(String description) {
        this.description = description;
        this.isDone = false;
    }

    // Standard getter for the task description.
    public String getDescription() {
        return description;
    }

    // Standard getter for the task status.
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the completion status of the task.
     * @param done The new completion status.
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Provides a displayable string representation of the task.
     * Includes the status [X] or [ ].
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }
}