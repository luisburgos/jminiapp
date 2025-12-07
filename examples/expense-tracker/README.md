# Expense Tracker Example

A simple financial application demonstrating the JMiniApp framework capabilities.

## Overview

This example allows users to track daily expenses, categorize them, and calculate totals. It demonstrates complex object state management, list manipulation, and automatic JSON persistence.

## Features

- **Add Expense**: Record description, amount, and category.
- **List All**: View all recorded expenses (supports live reload from disk).
- **Total Calculation**: Sum up all expenses.
- **Persistence**: Data is auto-saved to `expenses.json` immediately upon addition.

## Project Structure

expense-tracker/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── jminiapp/
                    └── examples/
                        └── expensetracker/
                            ├── Expense.java              # Data model
                            ├── ExpenseJSONAdapter.java   # JSON Format Adapter
                            ├── ExpenseTrackerApp.java    # Main logic
                            └── ExpenseTrackerRunner.java # Bootstrap



### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root**:
```bash
mvn clean install
Run the application
Option 1: Using Maven exec plugin (from the examples/expense-tracker directory)

Bash

cd examples/expense-tracker
mvn exec:java


Author Andry Rabanales