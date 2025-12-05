# Todo List Example

A simple todo list application demonstrating key features of the **JMiniApp** framework, including lifecycle management, persistent state handling, and JSON-based file operations.

## Overview

This example shows how to build a functional mini-application that manages a list of custom objects (`TodoItem`) using the JMiniApp core lifecycle.  
It demonstrates:

- How to maintain application state across executions  
- How to serialize and deserialize data using a custom `JSONAdapter`  
- How to implement a simple menu-driven CLI loop  

The app loads previous tasks automatically, allows users to add or complete tasks, and saves everything before exiting.

## Features

- **Add Task**: Create new tasks with descriptions  
- **Complete Task**: Mark tasks as completed  
- **Persistent State**: Task list is automatically restored on startup  
- **JSON Persistence**: Uses a custom `TodoJSONAdapter` for serialization  
- **Interactive Menu**: Clean CLI controlled by the `run()` loop  

## Project Structure

```
todo-list/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/todo/
    ├── TodoApp.java           # Main application logic (JMiniApp implementation)
    ├── TodoAppRunner.java     # Bootstrap configuration (Main entry point)
    ├── TodoItem.java          # The state model (POJO)
    └── TodoJSONAdapter.java   # Adapter for JSON file handling
```


## Key Components

### TodoItem

A simple POJO that represents a task.

Fields:
- `description`: The text describing the task  
- `isDone`: Boolean representing completion status  

Additional notes:
- Includes a no-argument constructor for JSON deserialization  

### TodoJSONAdapter

A JSON format adapter that implements `JSONAdapter<TodoItem>`.

Responsible for:

- Telling the framework how to read/write `TodoItem` objects  
- Allowing the app to use `context.getData()` and `context.setData()` for disk operations  
- Enabling automatic JSON-based persistence  

### TodoApp

Main application class extending `JMiniApp`.  
Implements the full lifecycle:

- **initialize()** – Loads saved data or starts with an empty list  
- **run()** – Displays menu, handles user input  
- **shutdown()** – Persists final task list before exit  

### TodoAppRunner

The bootstrap class that configures and launches the application.

It:

- Registers `TodoApp`, `TodoItem`, and `TodoJSONAdapter`  
- Defines the internal application name  
- Initializes the application engine  


## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/todo directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the todo example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/todo directory)
```bash
cd examples/todo
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/todo directory)
```bash
cd examples/todo
java -jar target/todo-app.jar
```

Option 3: From the project root
```bash
cd examples/todo && mvn exec:java
```

## Usage example

```
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

```
