package com.jminiapp.examples.shoppinglist;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ShoppingListApp extends JMiniApp {
    private ShoppingListState listState;
    private Scanner scanner;
    private boolean running = true;

    public ShoppingListApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Shopping List MiniApp ===");
        System.out.println("Welcome to the Shopping List App!");
        
        scanner = new Scanner(System.in);
        
        List<ShoppingListState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            listState = data.get(0);
            System.out.println("List loaded with " + listState.getItems().size() + " items.");
        } else {
            listState = new ShoppingListState();
            System.out.println("Starting with a new list.");
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
        context.setData(List.of(listState));
        scanner.close();
        System.out.println("\nList saved with " + listState.getItems().size() + " items.");
        System.out.println("Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n--- Items: " + listState.getItems().size() + " ---");
        System.out.println("1. View list");
        System.out.println("2. Add item");
        System.out.println("3. Remove item");
        System.out.println("4. Clear list");
        System.out.println("5. Export to JSON");
        System.out.println("6. Import from JSON");
        System.out.println("7. Exit");
        System.out.print("\nPlease choose an option: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    viewList();
                    break;

                case "2":
                    addItem();
                    break;

                case "3":
                    removeItem();
                    break;

                case "4":
                    clearList();
                    break;

                case "5":
                    exportToFile();
                    break;

                case "6":
                    importFromFile();
                    break;

                case "7":
                    running = false;
                    System.out.println("\nExiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1-7.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewList() {
        if (listState.getItems().isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("\n=== Your List ===");
            List<String> items = listState.getItems();
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    private void addItem() {
        System.out.print("Item name: ");
        String item = scanner.nextLine().trim();
        if (!item.isEmpty()) {
            listState.addItem(item);
            System.out.println("Item '" + item + "' added.");
        } else {
            System.out.println("Item name cannot be empty.");
        }
    }

    private void removeItem() {
        if (listState.getItems().isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        viewList();
        System.out.print("Number of the item to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < listState.getItems().size()) {
                String removed = listState.getItems().get(index);
                listState.getItems().remove(index);
                System.out.println("Item '" + removed + "' removed.");
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void clearList() {
        listState.clear();
        System.out.println("List cleared.");
    }

    private void exportToFile() {
        try {
            context.setData(List.of(listState));
            context.exportData("json");
            System.out.println("List successfully exported to: ShoppingList.json");
        } catch (IOException e) {
            System.out.println("Error exporting: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");
            List<ShoppingListState> data = context.getData();
            if (data != null && !data.isEmpty()) {
                listState = data.get(0);
                System.out.println("List successfully imported from ShoppingList.json!");
                System.out.println("Items loaded: " + listState.getItems().size());
            } else {
                System.out.println("Error: No valid data found in the file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing: " + e.getMessage());
        }
    }
}