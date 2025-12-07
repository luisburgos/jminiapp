package com.jminiapp.examples.expensetracker;

/**
 * Model class representing a single expense record.
 */
public class Expense {
    private String description;
    private double amount;
    private String category;

    // No-args constructor required for serialization
    public Expense() {}

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