package com.jminiapp.examples;

import com.jminiapp.core.api.*;
import java.util.*;

/**
 * A simple flashcards application demonstrating the JMiniApp framework.
 *
 * This app allows users to:
 * - Create a new flashcard
 * - Edit the current flashcard
 * - Delete the current flashcard
 * - View the current flashcard
 * - Save the flashcard and exit
 */

public class FlashcardsApp extends JMiniApp {
    private Scanner scanner;
    private List<FlashcardsState> flashcards;
    private boolean running;

    public FlashcardsApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Flashcards App ===");
        System.out.println("Welcome to the Flashcards App!");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing flashcard state from context
        List<FlashcardsState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            flashcards = new ArrayList<>(data);
            System.out.println("Loaded " + flashcards.size() + " flashcard(s).");
        } else {
            flashcards = new ArrayList<>();
            flashcards.add(new FlashcardsState("", ""));
            System.out.println("Starting with a new flashcard.");
        }
    }

    @Override
    protected void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        // Save the flashcards state to context
        List<FlashcardsState> data = new ArrayList<>(flashcards);
        context.setData(data);

        System.out.println("Flashcards saved. Exiting Flashcards App.");
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create New Flashcard");
        System.out.println("2. Edit Flashcard");
        System.out.println("3. Delete Flashcard");
        System.out.println("4. Review Flashcards");
        System.out.println("5. Show all Flashcards");
        System.out.println("6. Export to JSON file");
        System.out.println("7. Import from JSON file");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleUserInput() {
        String input = scanner.nextLine();
        int index;
        switch (input) {
            case "1":
                createFlashcard();
                break;
            case "2":
                System.out.println("Write the index of the flashcard to edit:");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                editFlashcard(index);
                break;
            case "3":
                System.out.println("Write the index of the flashcard to edit:");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                deleteFlashcard(index);
                break;
            case "4":
                System.out.println("Write the index of the flashcard to review:");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                reviewFlashcards(index);
                break;
            case "5":
                showAllFlashcards();
                break;
            case "6":
                exportToFile();
                break;
            case "7":
                importFromFile();
                break;
            case "8":
                running = false;
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        index = -1;
    }

    // edit export to make it work with the new list for flashcards
    private void exportToFile(){
        try{
            context.setData(new ArrayList<>(flashcards));
            context.exportData("json");
            System.out.println("Flashcard exported to JSON file successfully.");
        }
        catch(Exception e){
            System.out.println("Error exporting flashcard: " + e.getMessage());
        }
    }


    // edit import to make it work with the new list for flashcards
    private void importFromFile(){
        try{
            context.importData("json");
            List<FlashcardsState> data = context.getData();
            if(data != null && !data.isEmpty()){
                flashcards = new ArrayList<>(data);
                System.out.println("Flashcards imported successfully");
            }
            else{
                System.out.println("No flashcard data found in the imported file.");
            }
        }
        catch(Exception e){
            System.out.println("Error importing flashcard: " + e.getMessage());
        }
    }

    private void createFlashcard() {
        FlashcardsState comparison = new FlashcardsState("","");
        if(flashcards.size() == 1 && flashcards.contains(comparison)){
            flashcards.remove(0);
        }

        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter answer: ");
        String answer = scanner.nextLine();
        flashcards.add(new FlashcardsState(question, answer));
        System.out.println("Flashcard created.");
    }

    private void editFlashcard(int index) {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards to edit.");
            return;
        }
        try{
            FlashcardsState flashcard = flashcards.get(index);
            System.out.print("Current question: " + flashcard.getQuestion() + "\nEnter new question: ");
            String question = scanner.nextLine();
            System.out.print("Current answer: " + flashcard.getAnswer() + "\nEnter new answer: ");
            String answer = scanner.nextLine();
            flashcard.setQuestion(question);
            flashcard.setAnswer(answer);
            System.out.println("Flashcard updated.");
        }
        catch(Exception e){ // change it to detect the especific error index out of bound
            System.out.println("Number of flashcard unexistant.");
        } // add another exception to catch an unhandled
    }

    private void deleteFlashcard(int index){
        if (flashcards.isEmpty()){
            System.out.println("No flashcards to edit");
            return;
        }
            index -= 1; // adjust for 0-based index
        flashcards.remove(index);
        System.out.println("Flashcard deleted.");
    }

    private void reviewFlashcards(int index){
        if(flashcards.isEmpty()){
            System.out.println("There are no flashcards");
            return;
        }
        FlashcardsState flashcard = flashcards.get(index);
        System.out.println("Question: " + flashcard.getQuestion());
        System.out.println("Press Enter to see the answer...");
        scanner.nextLine();
        System.out.println("Answer: " + flashcard.getAnswer());
        scanner.nextLine(); 
    }

    private void showAllFlashcards() {
        if (flashcards.isEmpty()) {
            System.out.println("There are no flashcards to show.");
            return;
        }
        for (int i = 0; i < flashcards.size(); i++) {
            FlashcardsState flashcard = flashcards.get(i);
            System.out.println((i + 1) + ". Question: " + flashcard.getQuestion() + " | Answer: " + flashcard.getAnswer());
        }
    }
}