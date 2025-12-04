# Todo List Example

A simple task management application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a mini-app using JMiniApp core that manages a list of tasks. Users can add, list, complete, and delete tasks through an interactive command-line interface.

## Features

- **Add Task**: Create new tasks with a description
- **List Tasks**: View all tasks with their ID and status (Pending/Completed)
- **Complete Task**: Mark specific tasks as done using their ID
- **Delete Task**: Remove tasks from the list using their ID
- **Persistent State**: Tasks are automatically saved to a JSON file upon exit

## Project Structure

```
todo-list/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── todolist/
│                       ├── Task.java
│                       └── TodoListApp.java
└── target/
```

## Key Components

### Task

# Todo List Example

A simple task management application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a mini-app using JMiniApp core that manages a list of tasks. Users can add, list, complete, and delete tasks through an interactive command-line interface.

## Features

- **Add Task**: Create new tasks with a description
- **List Tasks**: View all tasks with their ID and status (Pending/Completed)
- **Complete Task**: Mark specific tasks as done using their ID
- **Delete Task**: Remove tasks from the list using their ID
- **Persistent State**: Tasks are automatically saved to a JSON file upon exit

## Project Structure

todolist/ ├── pom.xml ├── README.md └── src/main/java/com/example/todolist/ ├── TodoListApp.java # Main application class └── Task.java # Task model


## Key Components

### Task
A model class representing a single unit of work:
- Stores `id`, `description`, and `completed` status
- Provides constructors and getters/setters for data management

### TodoListApp
The main application class that extends `JMiniApp` and implements the lifecycle:
- `initialize()`: Loads existing tasks from the context or starts a new list
- `run()`: Interactive loop processing commands (`add`, `list`, `complete`, `delete`)
- `shutdown()`: Saves the current list of tasks to the context (auto-persisted to JSON)

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root**:
```bash
mvn clean install
```

### Run the application
Option 1: Using Maven exec plugin (from the examples/todolist directory)

```bash
cd examples/todolist
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/todolist directory after building)

```bash
cd examples/todolist
java -jar target/todo-list-1.0-SNAPSHOT.jar
```

### Usage Example

Welcome to Todo List App!
> list
No tasks yet.

> add Buy milk
Task added.

> add Walk the dog
Task added.

> list
[1] Buy milk - Pending
[2] Walk the dog - Pending

> complete 1
Task marked as completed.

> list
[1] Buy milk - Completed
[2] Walk the dog - Pending

> delete 2
Task deleted.

> list
[1] Buy milk - Completed

> exit

Upon exit, the application automatically saves your tasks to Task.json (or the configured resource file), allowing data persistence for the next session.

### Author
[José Pereira](https://github.com/Tony-Pereira05)