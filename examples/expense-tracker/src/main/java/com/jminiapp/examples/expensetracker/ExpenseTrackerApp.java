package com.jminiapp.examples.expensetracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jminiapp.core.api.ImportStrategies;
import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

public class ExpenseTrackerApp extends JMiniApp {
    private List<Expense> expenses;
    private Scanner scanner;
    private boolean running;

    public ExpenseTrackerApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("Initializing Expense Tracker...");
        scanner = new Scanner(System.in);
        
        try {
            context.importData("expenses.json", "json");
            System.out.println("Data loaded from expenses.json successfully!");
        } catch (Exception e) {
            System.out.println("No saved data found. Starting fresh.");
        }

        List<Expense> loadedData = context.getData();
        if (loadedData != null) {
            expenses = new ArrayList<>(loadedData);
            System.out.println("Loaded " + expenses.size() + " expenses.");
        } else {
            expenses = new ArrayList<>();
        }
        running = true;
    }

    @Override
    protected void run() {
        while (running) {
            showMenu();
            handleInput();
        }
    }

    @Override
    protected void shutdown() {
        context.setData(expenses);
        System.out.println("Expenses saved. Goodbye!");
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n--- Expense Tracker Menu ---");
        System.out.println("1. Add Expense");
        System.out.println("2. List Expenses");
        System.out.println("3. Show Total");
        System.out.println("4. Export to JSON");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");
    }

    private void handleInput() {
        try {
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    addExpense();
                    break;
                case "2":
                    listExpenses();
                    break;
                case "3":
                    showTotal();
                    break;
                case "4":
                    exportData();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error processing input: " + e.getMessage());
        }
    }

    private void addExpense() {
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Category (Food/Transport/etc): ");
        String cat = scanner.nextLine();

        expenses.add(new Expense(desc, amount, cat));
        System.out.println("Expense added!");

        try {
            context.setData(expenses);
     
            context.exportData("expenses.json", "json"); 
        } catch (Exception e) {
            System.out.println("Auto-save failed: " + e.getMessage());
        }
    }

    private void listExpenses() {
        try {
            context.importData("expenses.json", "json", ImportStrategies.REPLACE);
            
            if (context.getData() != null) {
                expenses = new ArrayList<>(context.getData());
            }
        } catch (Exception e) {
        }

        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\n--- Expenses List (Live from Disk) ---");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    private void showTotal() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("Total Spent: $%.2f%n", total);
    }

    private void exportData() {
        try {
            context.setData(expenses);
            context.exportData("expenses.json", "json");
            System.out.println("Data exported to expenses.json successfully.");
        } catch (Exception e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
}