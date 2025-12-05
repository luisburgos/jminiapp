# Flashcards Example

A command-line study tool application demonstrating the JMiniApp framework.

## Overview

This example shows how to create an interactive study application using JMiniApp core that manages a list of flashcards. Users can add new cards, remove them, list them, and enter a "Study Mode" to quiz themselves with random cards and track their memorization progress.

## Features

- **Study Mode**: Randomly selects a card you haven't memorized yet to quiz the user.
- **Track Progress**: Mark cards as "Memorized" to track learning status and remove them from the Study Mode section.
- **Deck Management**: Add new cards and remove unwanted ones.
- **Automatic Persistence**: Loads data on startup and saves changes automatically when exiting.
- **JSON Storage**: Data is stored in a human-readable `Flashcards.json` file.

## Project Structure

```
flashcards/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/flashcards/
    ├── FlashcardsApp.java         # Main application class
    ├── FlashcardAppRunner.java    # Bootstrap configuration
    ├── Flashcards.java            # Flashcards model
    └── FlashcardJSONAdapter.java  # JSON format adapter
```
## Key Components

### Flashcards
A simple model class that represents a single card with:
- `question`: The front of the card.
- `answer`: The back of the card.
- `isMemorized`: A boolean flag tracking if the user knows this card.

### FlashcardJSONAdapter
A format adapter that enables JSON import/export for `Flashcard` objects:
- Implements `JSONAdapter<Flashcard>` from the framework.
- Registers with the framework during app bootstrap.
- Provides automatic serialization/deserialization for the list of cards.

### FlashcardsApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Loads existing cards from `Flashcards.json` automatically.
- `run()`: Main loop displaying the interactive menu.
- `shutdown()`: Saves the list of cards to `Flashcards.json` before exiting.

### FlashcardsAppRunner
Bootstrap configuration that:
- Registers the `FlashcardJSONAdapter` with `.withAdapters()`.
- Configures the app name and model class (`Flashcard.class`).
- Launches the application.

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/flashcards directory):
```bash
mvn clean install
```
This will build both the jminiapp-core module and the flashcards example.This will build both the jminiapp-core module and the flashcards example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/flashcards directory)
```bash
cd examples/flashcards
mvn exec:java
```
Option 2: Using the packaged JAR (from the examples/flashcards directory)
```bash
cd examples/flashcards
java -jar target/flashcards-example.jar
```
Option 3: From the project root
```bash
cd examples/flashcards && mvn exec:java
```
### Usage Example

Running the app with cards already loaded from `Flashcards.json`:
```
=== Flashcard Study App ===
Loading data...
Loaded 3 flashcards.

--- Menu ---
1. Study Mode (Random Card)
2. Add New Card
3. Remove Card
4. List All Cards
5. Exit
   Select an option: 1

--------------------------------
QUESTION: Java keyword for inheritance?
--------------------------------
Press ENTER to reveal answer...

ANSWER: extends
Did you memorize this? (y/n): y
Marked as memorized!
```
If you mark all cards as memorized and try to study again:
```
--- Menu ---
...
Select an option: 1

Great job! You have memorized all cards!
```
Removing a card:
```
--- Menu ---
...
3. Remove Card
   ...
   Select an option: 3

--- All Flashcards ---
1. [X] Q: Capital of France?
2. [X] Q: Java keyword for inheritance?
3. [ ] Q: Value of Pi (2 decimals)?
   Which card do you want to remove?
   Enter number: 3
   Card removed!
```
Author: [Mauricio Emiliano Ramirez Ceciliano / Maur2012]