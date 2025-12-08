---
sidebar_position: 3
---

# Flashcards

A flashcard learning application demonstrating list management, specialized review functionality, and educational application patterns in JMiniApp.

**Source Code:** [examples/flashcards](https://github.com/jminiapp/jminiapp/tree/main/examples/flashcards)

## Features

- **Flashcard Management** - Create, Edit, and Delete question-answer pairs
- **Review Mode** - Interactive study mode with question-first, answer-reveal pattern
- **JSON Import/Export** - Save and load entire flashcard decks from files
- **Active Recall Learning** - Promotes effective memorization through recall practice
- **Index-Based Navigation** - Easily navigate large flashcard collections
- **State Persistence** - Flashcard decks automatically saved between study sessions

## Quick Start

```bash
cd examples/flashcards
mvn clean install
mvn exec:java
```

## Key Components

### State Model

Class representing individual flashcards with question and answer:

```java
public class FlashcardsState {
    private String question;
    private String answer;

    public FlashcardsState(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    
    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }
}
```

### Application Lifecycle

Managing a deck of flashcards with specialized review functionality:

```java
public class ContactsApp extends JMiniApp {
    private List<ContactsState> contacts;

    @Override
    protected void initialize() {
        // Load existing contacts or create empty list
        List<ContactsState> data = context.getData();
        contacts = data != null ? new ArrayList<>(data) : new ArrayList<>();
        
        System.out.println("Loaded " + contacts.size() + " contact(s).");
    }

    @Override
    protected void run() {
        // Main application loop with contact management menu
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        // Save all contacts for next run
        context.setData(new ArrayList<>(contacts));
        System.out.println("Contacts saved. Exiting Contacts App.");
    }
}
```

### JSON Adapter

Simple adapter for JSON format support:

```java
public class FlashcardsJSONAdapter implements JSONAdapter<FlashcardsState> {
    @Override
    public Class<FlashcardsState> getstateClass() {
        return FlashcardsState.class;
    }
}
```
### Bootstrap

Configure and launch the application:

```java
public class FlashcardsAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(FlashcardsApp.class)
            .withState(FlashcardsState.class)
            .withAdapters(new FlashcardsJSONAdapter())
            .named("Flashcards")
            .run(args);
    }
}
```

## Usage Example

Interactive menu with all operations:

```
=== Flashcards App ===
Welcome to the Flashcards App!
Loaded 3 flashcard(s).

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
1. Question: What is the capital of France? | Answer: Paris
2. Question: What is 2 + 2? | Answer: 4
3. Question: What is the chemical symbol for water? | Answer: H₂O

Choose an option: 4
Write the index of the flashcard to review: 1
Question: What is the capital of France?
Press Enter to see the answer...
[User presses Enter]
Answer: Paris
[User presses Enter to continue]

Choose an option: 6
Flashcard exported successfully
```

The exported JSON file:

```json
[
  {
    "question": "Question text here",
    "answer": "Answer text here"
  },
  {
    "question": "Another question",
    "answer": "Another answer"
  }
]
```

### Creating and Managing Study Decks

You can create topic-specific decks, export them, and share with others:

1. **Create a specialized deck** - Build flashcards for a specific subject:

```
Choose an option: 1
Enter question: What is the largest ocean on Earth?
Enter answer: Pacific Ocean
Flashcard created.

Choose an option: 1
Enter question: What is the longest river in the world?
Enter answer: Nile River
Flashcard created.
```

2. **Export your deck** - Save for backup or sharing:

```
Choose an option: 6
Flashcard exported successfully.
```

3. **Share and import decks** - Exchange with other learners:

```
=== Flashcards App ===
Welcome to the Flashcards App!
Loaded 0 flashcard(s).

Choose an option: 7
Flashcards imported successfully. Total flashcards: 2
```

### The Review Process
The app implements an effective learning workflow:

1. Select Review Mode (Option 4)
2. Choose Flashcard by Index
3. View Question First - Try to recall the answer
4. Press Enter to Reveal Answer - Check your recall
5. Self-Assess - Did you remember correctly?

This pattern promotes active recall, a scientifically-proven learning technique that strengthens memory retention more effectively than passive review.

## Key Concepts

This example demonstrates:

### List-Based State Management
- Handle collections of objects instead of single values
- Index-based operations for CRUD functionality
- Efficient list serialization/deserialization

### CRUD Operations Pattern
- **Create**: Add new contacts with contacts.add()
- **Read**: View individual or all contacts
- **Update**: Modify existing contact properties
- **Delete**: Remove contacts from the list

### Deck Management
- Complete Deck Export/Import: Save and load entire collections
- Topic Organization: Create subject-specific decks
- Progress Tracking: Visual feedback on deck size and content

## Related

- **[Build Your First App](../getting-started/build-your-first-app)** - Step-by-step tutorial
- **[Lifecycle Guide](../guides/lifecycle)** - Understanding the application lifecycle
- **[Context API](../guides/context)** - State management in depth
- **[Format Adapters](../guides/adapters)** - Working with different file formats