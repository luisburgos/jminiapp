# Contacts App Example

A contacts management application demonstrating the JMiniApp framework with JSON import/export capabilities.

## Overview

This example shows how to create a contacts management mini-app using the JMiniApp framework. Users can create, view, edit, delete, and manage multiple contacts through an interactive menu, with full JSON import/export functionality.

## Features
  
- **Create Contact**: Add new contacts with name and phone number
- **Edit Contact**: Modify existing contact information
- **Delete Contact**: Remove contacts from the list
- **View Contact**: Display individual contact details
- **Show All Contacts**: List all contacts with index numbers
- **Export to JSON**: Save all contacts to a JSON file
- **Import from JSON**: Load contacts from a JSON file

## Project Structure

```
contacts/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/
├── ContactsApp.java                # Main application class
├── ContactsAppRunner.java          # Bootstrap configuration
├── ContactsState.java              # Contact model class
└── ContactsJSONAdapter.java        # JSON format adapter
```

## Key Components

### ContactsState
A model class representing a contact with:
- `name`: Contact's name
- `phoneNumber`: Contact's phone number

### ContactsJSONAdapter
A format adapter that enables JSON import/export for `ContactsState`:
- Implements `JSONAdapter<ContactsState>` from the framework
- Handles serialization/deserialization of contact lists
- Registered with the framework during app bootstrap

### ContactsApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Loads existing contacts or starts with empty list
- `run()`: Main loop displaying menu and handling user input
- `shutdown()`: Saves contacts before exiting
- Uses framework's `context.importData()` and `context.exportData()` for JSON operations
- Provides full CRUD operations for contact management

### ContactsAppRunner
Bootstrap configuration that:
- Registers the `ContactsJSONAdapter` with `.withAdapters()`
- Configures the app name and model class
- Launches the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- JMiniApp framework dependencies

### Build the project

From the **project root** (not the examples/contacts directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the contacs example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/contacts directory)
```bash
cd examples/contacts
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/contacts directory)
```bash
cd examples/contacts
java -jar target/contacts-app.jar
```

Option 3: From the project root
```bash
cd examples/contacts && mvn exec:java
```

## Usage Example

### Basic Operations

```
=== Contacts App ===
Welcome to the Contacts App!
Loaded 0 contact(s).

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 1
Enter name: John Doe
Enter phone number: 555-1234
New contact created.

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 1
Enter name: Jane Smith
Enter phone number: 555-5678
New contact created.

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit

Choose an option: 5
1. Name: John Doe | Phone Number: 555-1234
2. Name: Jane Smith | Phone Number: 555-5678

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit
Choose an option: 4
Write the index of the contact to view: 1
Name: John Doe
Phone Number: 555-1234

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit
Choose an option: 2
Write the index of the contact to edit: 1
Enter new name (current: John Doe): Johnathan Doe
Enter new phone number (current: 555-1234): 555-9999
Contact updated.
```
### Export to JSON

```
Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit
Choose an option: 6
Enter filename to export (e.g., Contacts.json): my-contacts.json
Contacts exported to JSON file successfully.
```

The exported JSON file will look like:
```json
[
  {
    "name": "Johnathan Doe",
    "phoneNumber": "555-9999"
  },
  {
    "name": "Jane Smith",
    "phoneNumber": "555-5678"
  }
]
```
### Import from JSON

```
=== Contacts App ===
Welcome to the Contacts App!
Starting with a new contact.

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit
Choose an option: 7
Enter filename to import (e.g., Contacts.json): my-contacts.json
Contacts imported successfully.

Menu:
1. Create New Contact
2. Edit Contact
3. Delete Contact
4. View Contact
5. Show all Contacts
6. Export to JSON file
7. Import from JSON file
8. Exit
Choose an option: 5
1. Name: Johnathan Doe | Phone Number: 555-9999
2. Name: Jane Smith | Phone Number: 555-5678\
```