---
sidebar_position: 2
---

# Todo List Example Application

A task management application demonstrating list manipulation and interactive command processing.

### Features:
- Add and remove tasks
- Mark tasks as completed
- List view of all items
- Persistent JSON storage

**Source Code:** [examples/todolist](https://github.com/jminiapp/jminiapp/tree/main/examples/todolist)

### Key Concepts Demonstrated

- Handling List-based state
- Complex user input parsing
- Data model relationships (ID, Description, Status)
- Auto-save on shutdown

### Quick Start

```bash
cd examples/todolist
mvn clean install
mvn exec:java
```

### Code Highlights

```java
public class Task {
    private int id;
    private String description;
    private boolean completed;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    // Getters, setters, and toString() omitted for brevity
}   
```

### Application:

```java
public class TodoListApp extends JMiniApp {
    private List<Task> tasks;
    private int nextId = 1;

    public TodoListApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        // Load existing data
        List<Task> data = context.getData();
        tasks = data.isEmpty() ? new ArrayList<>() : new ArrayList<>(data);
        
        // Calculate next ID
        if (!tasks.isEmpty()) {
            nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        }
        System.out.println("Loaded " + tasks.size() + " tasks.");
    }

    @Override
    protected void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Commands: add <text>, list, complete <id>, delete <id>, exit");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.startsWith("add ")) {
                tasks.add(new Task(nextId++, input.substring(4)));
                System.out.println("Task added.");
            } else if (input.equals("list")) {
                tasks.forEach(System.out::println);
            } else if (input.equals("exit")) {
                running = false;
            }
            // Other commands (complete, delete) handled here...
        }
    }

    @Override
    protected void shutdown() {
        // Persist data
        context.setData(tasks);
        try {
            context.exportData("json");
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save tasks.");
        }
    }
}
```

### Bootstrap:

```java
public static void main(String[] args) {
    JMiniAppRunner
        .forApp(TodoListApp.class)
        .withState(Task.class)
        .named("Todo List")
        .run(args);
}
```

This example shows how to handle more complex data structures (Lists of Objects) and implement a command-line interface for CRUD operations within the JMiniApp lifecycle.