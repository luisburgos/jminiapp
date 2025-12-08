---
sidebar_position: 2
---

# Contacts

A contacts management application demonstrating CRUD operations, list management, and JSON import/export in JMiniApp.

**Source Code:** [examples/contacts](https://github.com/jminiapp/jminiapp/tree/main/examples/contacts)

## Features

- **CRUD Operations** - Create, Read, Update, and Delete contacts
- **List Management** - Handle multiple contacts with index-based operations
- **JSON Import/Export** - Save and load entire contact lists from files
- **Interactive Menu** - Console-based contact management interface
- **State Persistence** - Contact database automatically saved between runs

## Quick Start

```bash
cd examples/contacts
mvn clean install
mvn exec:java
```

## Key Components

### State Model

Class representing individual contacts with name and phone number:

```java
public class ContactsState {
    private String name;
    private String phoneNumber;

    public ContactsState(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    
    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
```

### Application Lifecycle

The three-phase lifecycle in action:

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
public class ContactsJSONAdapter implements JSONAdapter<ContactsState> {
    @Override
    public Class<ContactsState> getstateClass() {
        return ContactsState.class;
    }
}
```
### Bootstrap

Configure and launch the application:

```java
public class CounterAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(ContactsApp.class)
            .withState(ContactsState.class)
            .withAdapters(new ContacsJSONAdapter())
            .named("Contacts")
            .run(args);
    }
}
```

## Usage Example

Interactive menu with all operations:

```
=== Contacts App ===
Welcome to the Contacts App!
Loaded 2 contact(s).

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

Choose an option: 2
Write the index of the contact to edit: 1
Enter new name (current: John Doe): Johnathan Doe
Enter new phone number (current: 555-1234): 555-9999
Contact updated.

Choose an option: 6
Contacts exported to JSON file successfully.
```

The exported JSON file:

```json
[
  {
    "name": "Johnathan Doe",
    "phoneNumber": "555-9999"
  },
  {
    "name": "Jane Smith",
    "phoneNumber": "555-5678"
  },
  {
    "name": "Bob Johnson",
    "phoneNumber": "555-2468"
  }
]
```

### Editing and Importing Counter State

You can manually edit the exported JSON file and import it back:

1. **Edit the Contacs.json file** - Change the value to any number you want:

```json
[
  {
    "name": "Johnathan Doe",
    "phoneNumber": "555-9999"
  },
  {
    "name": "Jane Smith",
    "phoneNumber": "555-5678"
  },
  {
    "name": "Bob Johnson",
    "phoneNumber": "555-2468"
  }
]
```

2. **Import the modified file** - Use option 7 to load the updated contact list:

```
Choose an option: 7
Contacts imported successfully.

Choose an option: 5
1. Name: Johnathan Doe | Phone Number: 555-9999
2. Name: Jane Smith | Phone Number: 555-5678
3. Name: Bob Johnson | Phone Number: 555-2468
```

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

### Bulk Import/Export
- Export entire contact lists with a single command
- Import replaces current contact list with file contents
-   JSON format for easy manual editing and interoperability

## Related

- **[Build Your First App](../getting-started/build-your-first-app)** - Step-by-step tutorial
- **[Lifecycle Guide](../guides/lifecycle)** - Understanding the application lifecycle
- **[Context API](../guides/context)** - State management in depth
- **[Format Adapters](../guides/adapters)** - Working with different file formats