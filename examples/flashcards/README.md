# Flashcards App Example

A flashcard learning application demonstrating the JMiniApp framework with JSON import/export capabilities.

## Overview

This example shows how to create a flashcard learning mini-app using the JMiniApp framework. Users can create, edit, delete, review, and manage multiple flashcards through an interactive menu, with full JSON import/export functionality. Perfect for studying and memorization practice.

## Features

- **Create Flashcard**: Add new flashcards with question and answer pairs
- **Edit Flashcard**: Modify existing flashcard questions and answers
- **Delete Flashcard**: Remove flashcards from the deck
- **Review Flashcard**: Study mode that shows question first, then reveals answer
- **Show All Flashcards**: List all flashcards with index numbers
- **Export to JSON**: Save entire flashcard deck to a JSON file
- **Import from JSON**: Load flashcard deck from a JSON file
- **Persistent State**: Flashcards are automatically saved and loaded between sessions

## Project Structure

```
flashcards/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/
├── FlashcardsApp.java          # Main application class
├── FlashcardsAppRunner.java    # Bootstrap configuration
├── FlashcardsState.java        # Flashcard model class
└── FlashcardsJSONAdapter.java  # JSON format adapter
```

## Key Components


### FlashcardsState
A simple model class that represents a flashcard with:

- **question**: The front of the flashcard
- **answer**: The back of the flashcard

### FlashcardsJSONAdapter
A format adapter that enables JSON import/export for FlashcardsState:
- Implements JSONAdapter<FlashcardsState> from the framework
- Registers with the framework during app bootstrap
- Provides automatic serialization/deserialization for multiple flashcards

### FlashcardsApp
The main application class that extends JMiniApp and implements:

- `initialize()`: Set up the app and load existing flashcard collection
- `run()`: Main loop displaying menu and handling user input
- `shutdown()`: Save the flashcard collection before exiting
- `createFlashcard()`: Create new flashcards in the list
- `editFlashcard(int)`: Modify existing flashcards
- `deleteFlashcard(int)`: Remove flashcards from collection
- `reviewFlashcard`: Mode to review an especific flashcard
- `showAllFlashcards()`: Display all flashcards with questions and answers
- Uses framework's context.importData() and context.exportData() for file operations

### FlashcardsAppRunner
Bootstrap configuration that:
- Registers the `FlashcardsJSONAdapter` with `.withAdapters()`
- Configures the app name and model class
- Launches the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/counter directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the counter example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/flashcards directory)
```bash
cd examples/flashcards
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/flashcards directory)
```bash
cd examples/flashcards
java -jar target/flashcards-app.jar
```

Option 3: From the project root
```bash
cd examples/flashcards && mvn exec:java
```

## Usage Example

### Basic Operations

```
=== Flashcards App ===
Welcome to the Flashcards App!

Menu:
1. Create New Flashcard
2. Edit Flashcard
3. Delete Flashcard
4. Review Flashcards
5. Show all Flashcards
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 1
Enter question: How much is 2 + 2?
Enter answer: 4
Flashcard created.

Menu:
1. Create New Flashcard
2. Edit Flashcard
3. Delete Flashcard
4. Review Flashcards
5. Show all Flashcards
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 5
1. Question: How much is 2 + 2? | Answer: 4

```

### Export to JSON

```
Menu:
1. Create New Flashcard
2. Edit Flashcard
3. Delete Flashcard
4. Review Flashcards
5. Show all Flashcards
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 6
Flashcard exported successfully to: Flashcards.json
```

The exported JSON file will look like:
```json
[
  {
    "question": "Whats the simbol for Hydrogen?",
    "answer": "H"
  },
  {
    "question": "How much is  2 + 2",
    "answer": "4"
  }
]
```

### Import from JSON

```
Menu:
1. Create New Flashcard
2. Edit Flashcard
3. Delete Flashcard
4. Review Flashcards
5. Show all Flashcards
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 7
Flashcards imported successfully. Total flashcards: 2

```