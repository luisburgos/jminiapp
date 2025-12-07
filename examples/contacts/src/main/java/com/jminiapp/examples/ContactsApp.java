package com.jminiapp.examples;

import com.jminiapp.core.api.*;
import java.util.*;

/**
 * A simple contacts application demonstrating the JMiniApp framework.
 *
 * This app allows users to:
 * - Create a new contact
 * - Edit the current contact
 * - Delete the current contact
 * - View the current contact
 * - Save the contact and exit
 */

public class ContactsApp extends JMiniApp {
    private Scanner scanner;
    private List<ContactsState> contacts;
    private boolean running;

    public ContactsApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Contacts App ===");
        System.out.println("Welcome to the Contacts App!");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing contact state from context
        List<ContactsState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            contacts = new ArrayList<>(data);
            System.out.println("Loaded " + contacts.size() + " contact(s).");
        } else {
            contacts = new ArrayList<>();
            System.out.println("Starting with a new contact.");
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
        // Save the contacts state to context
        List<ContactsState> data = new ArrayList<>(contacts);
        context.setData(data);

        System.out.println("Contacts saved. Exiting Contacts App.");
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create New Contact");
        System.out.println("2. Edit Contact");
        System.out.println("3. Delete Contact");
        System.out.println("4. View Contact");
        System.out.println("5. Show all Contacts");
        System.out.println("6. Export to JSON file");
        System.out.println("7. Import from JSON file");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleUserInput() {
        String choice = scanner.nextLine();
        int index;
        switch (choice) {
            case "1":
                createNewContact();
                break;
            case "2":
                System.out.println("Write the index of the contact to edit:");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                editContact(index);
                break;
            case "3":
                System.out.println("Write the index of the contact to delete:");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                deleteContact(index);
                break;
            case "4":
                System.out.println("Write the index of the contact to view:");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                viewContact(index);
                break;
            case "5":
                showAllContacts();
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
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void exportToFile() {
        try{
            context.setData(new ArrayList<>(contacts));
            context.exportData("json");
            System.out.println("Contacts exported to JSON file successfully.");
        }
        catch(Exception e){
            System.out.println("Error exporting contacts: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try{
            context.importData("json");
            List<ContactsState> data = context.getData();
            if(data != null){
                contacts = new ArrayList<>(data);
                System.out.println("Contacts imported successfully.");
            } else {
                System.out.println("No contacts found in the imported file.");
            }
        }
        catch(Exception e){
            System.out.println("Error importing contacts: " + e.getMessage());
        }
    }

    private void createNewContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        contacts.add(new ContactsState(name, phoneNumber));
        System.out.println("New contact created.");
    }

    private void editContact(int index) {
        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact index.");
            return;
        }
        ContactsState contact = contacts.get(index);
        System.out.print("Enter new name (current: " + contact.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("Enter new phone number (current: " + contact.getPhoneNumber() + "): ");
        String phoneNumber = scanner.nextLine();
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        System.out.println("Contact updated.");
    }

    private void deleteContact(int index) {
        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact index.");
            return;
        }
        contacts.remove(index);
        System.out.println("Contact deleted.");
    }

    private void viewContact(int index) {
        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact index.");
            return;
        }
        ContactsState contact = contacts.get(index);
        System.out.println("Name: " + contact.getName());
        System.out.println("Phone Number: " + contact.getPhoneNumber());
    }

    private void showAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("There are no contacts to show.");
            return;
        }
        for (int i = 0; i < contacts.size(); i++) {
            ContactsState contact = contacts.get(i);
            System.out.println((i + 1) + ". Name: " + contact.getName() + " | Phone Number: " + contact.getPhoneNumber());
        }
    }
}