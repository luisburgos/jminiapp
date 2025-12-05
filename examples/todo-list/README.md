Todo List Example

A simple task management application demonstrating key features of the JMiniApp framework, including state persistence and file handling.

Overview

This example showcases how to use the JMiniApp lifecycle to manage a collection of custom Java objects (TodoItem). It allows users to view, add, and complete tasks, and ensures the list is automatically saved and reloaded between runs.

Features

Add Task: Adds a new item to the list.

Complete Task: Marks an existing task as finished.

Persistent State: The entire task list is saved and restored automatically by the framework.

JSON Adapter: Configured to handle import/export of the task list to a JSON file.

Project Structure

todo-list/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/todo/
    ├── TodoApp.java           # Main application logic (JMiniApp implementation)
    ├── TodoAppRunner.java     # Bootstrap configuration (main method)
    ├── TodoItem.java          # The state model (a single task)
    └── TodoJSONAdapter.java   # JSON format adapter for TodoItem


Key Components

TodoItem (TodoItem.java)

This is the State Model representing a single task. It contains:

description: The task text.

isDone: A boolean flag indicating if the task is complete.

TodoJSONAdapter (TodoJSONAdapter.java)

This is the Format Adapter. It implements JSONAdapter<TodoItem> to enable the framework to automatically convert the list of TodoItem objects into a JSON format for saving and loading from the disk.

TodoApp (TodoApp.java)

This is the core logic and implements the JMiniApp lifecycle:

initialize(): Checks the context for saved tasks and loads them, or starts with an empty list.

run(): The main loop that displays the menu (A to Add, C to Complete, X to Exit) and processes user input.

shutdown(): Saves the final list of tasks back to the context for persistence before the application closes.

TodoAppRunner (TodoAppRunner.java)

The Bootstrap configuration, which ties everything together:

Registers TodoApp.class as the main application.

Registers TodoItem.class as the persistent state model.

Registers TodoJSONAdapter to enable file persistence.

Building and Running

Prerequisites

Java 17 or higher

Maven 3.6 or higher

Build the project

From the project root directory (jminiapp/):

mvn clean install


Run the application

From the examples/todo-list directory:

mvn exec:java


Usage Example

--- Initializing Todo List App ---
No existing tasks found. Starting fresh.

--- My Tasks ---
(No tasks yet)
----------------
A. Add Task
C. Complete Task
X. Exit
Select option: A
Enter task description: Finish JMiniApp project

--- My Tasks ---
1. [ ] Finish JMiniApp project
----------------
A. Add Task
C. Complete Task
X. Exit
Select option: A
Enter task description: Buy milk

--- My Tasks ---
1. [ ] Finish JMiniApp project
2. [ ] Buy milk
----------------
A. Add Task
C. Complete Task
X. Exit
Select option: C
Enter number to complete: 1

--- My Tasks ---
1. [X] Finish JMiniApp project
2. [ ] Buy milk
----------------
A. Add Task
C. Complete Task
X. Exit
Select option: X
Saving tasks...
Goodbye!
