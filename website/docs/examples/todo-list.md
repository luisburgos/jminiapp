---
title: Todo List Example
---

# Building the Todo List App with JMiniApp

This tutorial guides you through creating a Todo List app, similar to the Counter example but with task management.

## Step 1: Define the Task Model
Create `Task.java` (as shown in the code above).

## Step 2: Implement the App Logic
Extend `JMiniApp` in `TodoListApp.java`:
- Load tasks in `initialize()`.
- Handle commands in `run()`.
- Save in `shutdown()`.

## Step 3: Configure pom.xml
Add JMiniApp dependency and exec plugin (as above).

## Step 4: Test and Run
Build with `mvn clean package` and run with `mvn exec:java`. Add tasks and verify persistence.

For full code, see `examples/todo-list/`.

## Concepts Demonstrated
- State persistence with JSON adaptor.
- Interactive console loop.
- Lifecycle management.