# Todo List Example

A simple task management application demonstrating key features of the JMiniApp framework, including state persistence, lifecycle management, and file handling.

## Overview

This example shows how to create a functional mini-app that manages a collection of custom objects (TodoItem). It demonstrates how to persist a list of objects automatically using the framework's context and how to export/import that data using a JSON adapter.

## Features

* Add Task: Create new tasks with descriptions.

* Complete Task: Mark existing tasks as done.

* Persistent State: The task list is automatically saved and restored between application runs.

* JSON Persistence: Uses a custom JSONAdapter to handle data serialization to disk.

* Interactive Menu: Simple CLI interface managed by the run() loop.

## Project Structure

todo-list/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/todo/
    ├── TodoApp.java           # Main application logic (JMiniApp implementation)
    ├── TodoAppRunner.java     # Bootstrap configuration (Main entry point)
    ├── TodoItem.java          # The state model (POJO)
    └── TodoJSONAdapter.java   # Adapter for JSON file handling
## Key Components

### TodoItem (TodoItem.java)

The State Model representing a single unit of data. It contains:

* description: The text of the task.

* isDone: A boolean flag for the task status.

* Includes an empty constructor required for successful deserialization.

### TodoJSONAdapter (TodoJSONAdapter.java)

The Format Adapter that implements JSONAdapter<TodoItem>.

* It tells the framework exactly which class type (TodoItem.class) to use when reading or writing JSON files.

* Enables the context.getData() and context.setData() methods to work with files on disk.

### TodoApp (TodoApp.java)

The core application class extending JMiniApp. It implements the lifecycle:

initialize(): Loads existing tasks from the context or creates a new list.

run(): Handles the main user interface loop (Menu display and Input handling).

shutdown(): Saves the current state of tasks back to the context before the program exits.

### TodoAppRunner (TodoAppRunner.java)

The Bootstrap class that configures the engine:

* Connects the App (TodoApp), the State (TodoItem), and the Adapter (TodoJSONAdapter).

* Sets the internal application name used for file generation (e.g., MyTodoList.json).

## Building and Running

### Prerequisites

* Java 17 or higher

* Maven 3.6 or higher

### 1. Build the project

From the root of the jminiapp repository:

Bash

mvn clean install
### 2. Run the application

Navigate to the example directory and run using Maven:

Bash

cd examples/todo-list
mvn exec:java
## Usage Example

--- Initializing Todo List App ---
Loaded 2 tasks.

--- My Tasks ---
1. [X] Learn JMiniApp
2. [ ] Create a Pull Request
----------------
A. Add Task
C. Complete Task
X. Exit
Select option: C
Enter number to complete: 2

--- My Tasks ---
1. [X] Learn JMiniApp
2. [X] Create a Pull Request
----------------
A. Add Task
C. Complete Task
X. Exit
Select option: X
Saving tasks...
Goodbye!
