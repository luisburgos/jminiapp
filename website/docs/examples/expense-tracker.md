---
sidebar_position: 2
---


## Overview

In this tutorial, we will build an **Expense Tracker**. Unlike the simple Counter example, this app manages a list of complex objects (`Expense`), demonstrating how JMiniApp handles structured data and JSON serialization.:) 

## Step 1: The Model

First, we define what an "Expense" looks like. Create `Expense.java`:

```java
public class Expense {
    private String description;
    private double amount;
    private String category;

    public Expense() {} // Required for JSON deserialization

    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f", category, description, amount);
    }
}
Step 2: The Adapter
To save our expenses to a file, we need an adapter. We extend the built-in JSONAdapter:

Java

import com.jminiapp.core.adapters.JSONAdapter;

public class ExpenseJSONAdapter implements JSONAdapter<Expense> {
    @Override
    public Class<Expense> getstateClass() {
        return Expense.class;
    }
}
Step 3: The Application Logic
Extend JMiniApp to create the controller logic. We override initialize to load data and run to handle the menu.

Key features implemented:

Auto-save: We call context.exportData("json") immediately after adding an expense.

Live Reload: We use ImportStrategies.REPLACE when listing to see external changes.

Java

public class ExpenseTrackerApp extends JMiniApp {
    private List<Expense> expenses;

    @Override
    protected void initialize() {
        // Load data from context (file persistence)
        try {
            context.importData("expenses.json", "json");
        } catch (Exception e) {
            // Ignore if file doesn't exist
        }
        
        List<Expense> loaded = context.getData();
        expenses = loaded != null ? new ArrayList<>(loaded) : new ArrayList<>();
    }

    @Override
    protected void run() {
        // ... menu loop implementation ...
    }

    @Override
    protected void shutdown() {
        // Save data back to context
        context.setData(expenses);
    }
}
Step 4: Running the App
Finally, use the JMiniAppRunner to wire everything together:

Java

public static void main(String[] args) {
    JMiniAppRunner
        .forApp(ExpenseTrackerApp.class)
        .withState(Expense.class)
        .withAdapters(new ExpenseJSONAdapter())
        .named("ExpenseTracker")
        .run(args);
}

