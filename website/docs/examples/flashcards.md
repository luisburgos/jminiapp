# Flashcards Example Application

Build an interactive study tool to learn about random data access, list management, and updating state.

**Features:**
- **Smart Study Mode**: Prioritizes cards you haven't memorized yet.
- **Progress Tracking**: Tracks "Memorized" vs "Pending" status.
- **Deck Management**: Add and remove cards dynamically.
- **Automatic Persistence**: Saves/Loads data automatically on start and exit.

**Source Code:** [examples/flashcards](https://github.com/jminiapp/jminiapp/tree/main/examples/flashcards)
## Overview

In this tutorial, we will create a **Flashcard App**. This app allows users to quiz themselves, track which cards they have memorized, and manage their deck by adding or removing cards.

It demonstrates advanced JMiniApp patterns like **automatic persistence** (loading on start, saving on exit) and **smart filtering** (studying only what you don't know).

### Quick Start

```bash
cd examples/flashcards
mvn clean install
mvn exec:java
```

## Step 1: Define the Model

Our `Flashcard` model needs a question, an answer, and a boolean flag to track if it's memorized.

```java
public class Flashcards {
    private String question;
    private String answer;
    private boolean isMemorized;

    public Flashcards() {
    }

    public Flashcards(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.isMemorized = false; // Default to not memorized
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isMemorized() {
        return isMemorized;
    }

    public void setMemorized(boolean memorized) {
        isMemorized = memorized;
    }

    @Override
    public String toString() {
        return String.format("[%s] Q: %s", (isMemorized ? "X" : " "), question);
    }
}
```
## Step 2: The Adapter
We reuse the JSONAdapter pattern. JMiniApp handles the serialization of the isMemorized field automatically.

```java
public class FlashcardJSONAdapter implements JSONAdapter<Flashcard> {
    @Override
    public Class<Flashcard> getstateClass() {
        return Flashcard.class;
    }
}
```
## Step 3: Application
The core logic resides in `studyMode()`. We first filter the list to find only cards that are not memorized, and then pick a random one from that subset.
```java

public class FlashcardsApp extends JMiniApp {
private List<Flashcards> flashcards;
private boolean running;
private Scanner scanner;
private Random random;

    public FlashcardsApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("=== Flashcard Study App ===");
        scanner = new Scanner(System.in);
        random = new Random();
        running = true;
        try {
            System.out.println("Loading data...");
            context.importData("json");
        } catch (Exception e) {
            System.out.println("No save file found. Starting fresh.");
        }
        List<Flashcards> loadedData = context.getData();
        if (loadedData == null) {
            flashcards = new ArrayList<>();
        } else {
            flashcards = new ArrayList<>(loadedData);
        }
        System.out.println("Loaded " + flashcards.size() + " flashcards");
    }

    @Override
    protected void run() {
        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
    }

    @Override
    protected void shutdown() {
        context.setData(flashcards);
        try {
            context.exportData("json");
            System.out.println("Progress saved to Flashcards.json!");
        } catch (Exception e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
        scanner.close();
        System.out.println("Progress saved. Keep studying!");
    }
    private void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Study Mode (Random Card)");
        System.out.println("2. Add New Card");
        System.out.println("3. Remove Card");
        System.out.println("4. List All Cards");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                studyMode();
                break;
            case "2":
                addCard();
                break;
            case "3":
                removeCard();
                break;
            case "4":
                listCards();
                break;
            case "5":
                running = false;
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    private void studyMode() {
        List<Flashcards> pendingCards = new ArrayList<>();
        for (Flashcards card : flashcards) {
            if (!card.isMemorized()) {
                pendingCards.add(card);
            }
        }

        if (pendingCards.isEmpty()) {
            if (flashcards.isEmpty()) {
                System.out.println("No cards available. Add some first!");
            } else {
                System.out.println("Great job! You have memorized all cards!");
            }
            return;
        }

        Flashcards card = pendingCards.get(random.nextInt(pendingCards.size()));

        System.out.println("\n--------------------------------");
        System.out.println("QUESTION: " + card.getQuestion());
        System.out.println("--------------------------------");

        System.out.print("Press ENTER to reveal answer...");
        scanner.nextLine();

        System.out.println("ANSWER: " + card.getAnswer());

        System.out.print("Did you memorize this? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            card.setMemorized(true);
            System.out.println("Marked as memorized!");
        } else if (response.equals("n")) {
            card.setMemorized(false);
            System.out.println("Marked as not memorized.");
        }
    }

    private void addCard() {
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter answer: ");
        String answer = scanner.nextLine();
        flashcards.add(new Flashcards(question, answer));
        System.out.println("Card added!");
    }

    private void removeCard() {
        if (!listCards()) {
            return;
        }
        System.out.println("Which card do you want to remove?");
        try{
            String choice = scanner.nextLine();
            int cardToRemove = Integer.parseInt(choice);

            if(cardToRemove <= flashcards.size() && cardToRemove > 0){
                flashcards.remove(cardToRemove - 1);
                System.out.println("Card removed!");
            }
            else{
                System.out.println("Invalid card number!");
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input!");
        }
    }

    private boolean listCards() {
        System.out.println("\n--- All Flashflashcards ---");
        if (flashcards.isEmpty()) {
            System.out.println("No cards found.");
            return false;
        } else {
            for (int i = 0; i < flashcards.size(); i++) {
                System.out.println((i + 1) + ". " + flashcards.get(i));
            }
        }
        return true;
    }
}
```

## Step 4: The Runner
Finally, bootstrap the application.
```java
public class FlashcardsAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(FlashcardsApp.class)
            .named("Flashcards")
            .withState(Flashcards.class)
            .withAdapters(new FlashcardJSONAdapter())
            .run(args);
    }
}
```
This example demonstrates how to build a stateful application where the state changes based on user interaction (memorizing cards) and persists automatically between sessions.