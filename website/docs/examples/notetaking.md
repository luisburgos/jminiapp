---
sidebar_position: 4
---

# Note Taking

A simple note-taking application demonstrating rich text management, single-object state persistence, and content-focused application patterns in JMiniApp.

**Source Code:** [examples/notetaking](https://github.com/jminiapp/jminiapp/tree/main/examples/notetaking)

## Features

- **Rich Note Management** - Handle notes with both title and multi-line content
- **Single Object State** - Manage a single comprehensive note object
- **JSON Import/Export** - Save and load notes with full formatting preservation
- **Clear/Reset Functionality** - Easily start fresh with empty notes
- **Content-Focused Interface** - Always display current note alongside menu options
- **State Persistence** - Notes automatically saved between sessions

## Quick Start

```bash
cd examples/notetaking
mvn clean install
mvn exec:java
```

## Key Components

### State Model

Class representing a comprehensive note with title and content:

```java
public class NotetakingState {
    private String title;
    private String content;

    public NotetakingState(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    
    public void clear() {
        this.title = "";
        this.content = "";
    }
}
```

### Application Lifecycle

Managing a single rich note object with comprehensive content:

```java
public class NotetakingApp extends JMiniApp {
    private NotetakingState note;

    @Override
    protected void initialize() {
        // Load existing note or create empty note
        List<NotetakingState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            note = data.get(0);
            System.out.println("Loaded existing note: " + note.getTitle());
            System.out.println(note.getContent());
        } else {
            note = new NotetakingState("", "");
            System.out.println("Starting with a new note.");
        }
    }

    @Override
    protected void run() {
        // Main application loop with note always visible
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        // Save the single note for next session
        List<NotetakingState> data = new ArrayList<>(List.of(note));
        context.setData(data);
        
        System.out.println("\nFinal note saved: " + note.getTitle());
        System.out.println(note.getContent());
        System.out.println("Goodbye!");
    }
}
```

### JSON Adapter

Simple adapter for JSON format support:

```java
public class NotetakingJSONAdapter implements JSONAdapter<NotetakingState> {
    @Override
    public Class<NotetakingState> getstateClass() {
        return NotetakingState.class;
    }
}
```

### Bootstrap

Configure and launch the application:

```java
public class NotetakingAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(NotetakingApp.class)
            .withState(NotetakingState.class)
            .withAdapters(new NotetakingJSONAdapter())
            .named("Note Taking App")
            .run(args);
    }
}
```

## Usage Example

Interactive note-taking environment with always-visible content:

```
=== Note Taking App ===
Welcome to the Note Taking App!
Starting with a new note.

Current Note:
Title: 
Content: 

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit

Choose an option: 1
Enter new title: Meeting Notes - Project Alpha
Enter new content: Discussion Points:
- Deadline extension requested by client
- Team needs additional resources
- Next meeting scheduled for Friday

Note updated.

Current Note:
Title: Meeting Notes - Project Alpha
Content: Discussion Points:
- Deadline extension requested by client
- Team needs additional resources
- Next meeting scheduled for Friday

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit

Choose an option: 3
Note exported successfully.
```

The exported JSON file:

```json
[
  {
    "title": "Weekly Shopping List",
    "content": "Grocery Store:\n- Milk\n- Eggs\n- Bread\n- Coffee\n\nPharmacy:\n- Toothpaste\n- Vitamins\n- Prescription refill\n\nHardware Store:\n- Light bulbs\n- Batteries"
  }
]
```

### Exporting and Importing Notes

You can export notes for backup, editing in other tools, or transferring between systems:

1. **Export your note** - Save with descriptive filename:

```
Choose an option: 3
Note exported successfully.
```

2. **Edit the JSON file externally** - Add more content using your favorite text editor:

```json
[
  {
    "title": "Weekly Shopping List",
    "content": "Grocery Store:\n- Milk\n- Eggs\n- Bread\n- Coffee\n\nPharmacy:\n- Toothpaste\n- Vitamins\n- Prescription refill\n\nHardware Store:\n- Light bulbs\n- Batteries"
  }
]
```

3. **Import the enhanced note** - Load it back into the app:
```
Current Note:
Title: Meeting Notes - Project Alpha
Content: Discussion Points:
- Deadline extension requested by client
- Team needs additional resources
- Next meeting scheduled for Friday

Choose an option: 4
Note imported successfully.

Current Note:
Title: Weekly Shopping List
Content: Grocery Store:
- Milk
- Eggs
- Bread
- Coffee

Pharmacy:
- Toothpaste
- Vitamins
- Prescription refill

Hardware Store:
- Light bulbs
- Batteries
```

## Key Concepts

This example demonstrates:

### Lifecycle Management
- **initialize()** - Load existing state or create new
- **run()** - Execute main application logic
- **shutdown()** - Save state before exit

### Single Object State Management
- Rich Object Structure: Multiple fields (title, content) in a single object
- Context Preservation: Always display current state alongside actions
- Clear Operations: Simple reset functionality for the entire object

### Multi-Line Content Handling
- Line Break Preservation: JSON properly stores multi-line content
- Readable Display: Content shown with original formatting
- Easy Editing: Simple prompt-based editing for both title and content

## Related

- **[Build Your First App](../getting-started/build-your-first-app)** - Step-by-step tutorial
- **[Lifecycle Guide](../guides/lifecycle)** - Understanding the application lifecycle
- **[Context API](../guides/context)** - State management in depth
- **[Format Adapters](../guides/adapters)** - Working with different file formats