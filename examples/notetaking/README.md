# Note Taking App Example

A simple note-taking application demonstrating the JMiniApp framework with JSON import/export capabilities.

## Overview

This example shows how to create a note-taking mini-app using the JMiniApp framework. Users can create, edit, delete, and manage notes through an interactive menu, with full JSON import/export functionality. The app manages a single note at a time with title and content.

## Features

- **Edit Note**: Create or modify a note with title and content
- **Delete Note**: Clear the current note (reset to empty)
- **View Note**: Display the current note's title and content
- **Export to JSON**: Save the current note to a JSON file
- **Import from JSON**: Load a note from a JSON file

## Project Structure

```
notetaking/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/
├── NotetakingApp.java              # Main application class
├── NotetakingAppRunner.java        # Bootstrap configuration
├── NotetakingState.java            # Note model class
└── NotetakingJSONAdapter.java      # JSON format adapter
```

## Key Components

### NotetakingState
A model class representing a note with:
- `title`: Note title/header
- `content`: Note body/content
- `clear()`: Method to reset both title and content to empty strings

### NotetakingJSONAdapter
A format adapter that enables JSON import/export for `NotetakingState`:
- Implements `JSONAdapter<NotetakingState>` from the framework
- Handles serialization/deserialization of note data
- Registered with the framework during app bootstrap

### NotetakingApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Loads existing note or starts with empty note
- `run()`: Main loop displaying current note and menu options
- `shutdown()`: Saves the note before exiting
- Uses framework's `context.importData()` and `context.exportData()` for JSON operations
- Provides note editing and management functionality

### NotetakingAppRunner
Bootstrap configuration that:
- Registers the `NotetakingJSONAdapter` with `.withAdapters()`
- Configures the app name and model class
- Launches the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- JMiniApp framework dependencies

### Build the project

From the **project root** (not the examples/notetaking directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the notetaking example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/notetaking directory)
```bash
cd examples/notetaking
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/notetaking directory)
```bash
cd examples/notetaking
java -jar target/notetaking-app.jar
```

Option 3: From the project root
```bash
cd examples/notetaking && mvn exec:java
```

## Usage Example

### Basic Operations

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
Enter new title: Shopping List
Enter new content: - Milk
- Eggs
- Bread
- Coffee
Note updated.

Current Note:
Title: Shopping List
Content: - Milk
- Eggs
- Bread
- Coffee

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit
Choose an option: 1
Enter new title: Updated Shopping List
Enter new content: - Milk (2 liters)
- Eggs (dozen)
- Whole Wheat Bread
- Coffee Beans
- Cheese
Note updated.

Current Note:
Title: Updated Shopping List
Content: - Milk (2 liters)
- Eggs (dozen)
- Whole Wheat Bread
- Coffee Beans
- Cheese

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit
Choose an option: 2
Note deleted.

Current Note:
Title: 
Content: 

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit
```

### Export to JSON

```
Current Note:
Title: Meeting Notes
Content: Project discussion:
- Deadline: Friday
- Team: John, Sarah, Mike
- Budget: $10,000

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit
Choose an option: 3
Enter filename to export (e.g., Note.json): meeting-notes.json
Note exported successfully.
```

The exported JSON file will look like:
```json
[
  {
    "title": "Meeting Notes",
    "content": "Project discussion:\n- Deadline: Friday\n- Team: John, Sarah, Mike\n- Budget: $10,000"
  }
]
```

### Import from JSON

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
Choose an option: 4
Enter filename to import (e.g., Note.json): meeting-notes.json
Note imported successfully.

Current Note:
Title: Meeting Notes
Content: Project discussion:
- Deadline: Friday
- Team: John, Sarah, Mike
- Budget: $10,000

Menu:
1. Edit Note
2. Delete Note
3. Export to JSON file
4. Import from JSON file
5. Exit
```