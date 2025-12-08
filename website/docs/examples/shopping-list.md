---
sidebar_position: 2
---

# 🛒 Shopping List MiniApp (Java)

This tutorial guides you through creating a **Shopping List** application using the JMiniApp Java framework. This example demonstrates key concepts for managing a collection of data, handling user input, and implementing robust persistence (saving and loading state).

## 1. Project Setup and Maven Configuration

### A. Create the Structure
Navigate to the `examples/` directory and create the `shopping-list` module with the necessary Maven and Java package structure:

```bash
# From jminiapp/
mkdir examples/shopping-list
mkdir -p examples/shopping-list/src/main/java/com/jminiapp/examples/shoppinglist
mkdir examples/shopping-list/src/main/resources
```
B. Configure pom.xml
Create the pom.xml file inside examples/shopping-list/. This configuration links to the jminiapp-core and includes the gson library, which is essential for JSON serialization.

```bash
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.jminiapp</groupId>
        <artifactId>examples</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <artifactId>shopping-list-example</artifactId>
    <name>Shopping List Example</name>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>com.jminiapp</groupId>
            <artifactId>jminiapp-core</artifactId>
            <version>${project.parent.version}</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <finalName>${project.artifactId}</finalName>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
## 2. Defining the Application State (ShoppingListState)
The state class holds the application data that will be persisted.

### A. ShoppingListState.java
This class holds the list of items. It includes the necessary methods (addItem, getItems, setItems) for Gson to successfully load and save the data.
 ```bash
 package com.jminiapp.examples.shoppinglist;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListState {
    private List<String> items;

    public ShoppingListState() {
        // Ensure the list is always initialized
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        this.items.add(item);
    }

    public List<String> getItems() {
        return items;
    }

    // GSON REQUIRES this setter to inject the deserialized list upon loading.
    public void setItems(List<String> items) {
        this.items = items;
    }

    public void clear() {
        this.items.clear();
    }
}
```
## 3. Implementing the JSON Adapter
The adapter tells the framework which state class to use for JSON serialization. This step is crucial for the persistence and the manual export/import functionalities to recognize and handle the ShoppingListState.

## A. ShoppingListJSONAdapter.java
``` bash
package com.jminiapp.examples.shoppinglist;

import com.jminiapp.core.adapters.JSONAdapter;

public class ShoppingListJSONAdapter implements JSONAdapter<ShoppingListState> {

    @Override
    public Class<ShoppingListState> getstateClass() {
        return ShoppingListState.class;
    }
}
```
## 4. Building the Application Logic (ShoppingListApp)
This class handles the core loop, menu, user input, and all list operations. It is the heart of the application, managing state loading/saving and user interaction.

### A. ShoppingListApp.java
This implementation includes all menu options (Add, Remove, Clear, Export, Import) and the robust persistence logic required by the framework.


```bash
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
        
        // CRITICAL: Handle loading state (null check and empty list check)
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
        // Persist the current state before closing
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
        System.out.println("7. Exit (Saves Automatically)");
        System.out.print("\nPlease choose an option: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": viewList(); break;
                case "2": addItem(); break;
                case "3": removeItem(); break;
                case "4": clearList(); break;
                case "5": exportToFile(); break;
                case "6": importFromFile(); break;
                case "7": running = false; System.out.println("\nExiting..."); break;
                default: System.out.println("Invalid option. Please choose 1-7.");
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
                // Remove the item and get its name in one operation
                String removed = listState.getItems().remove(index); 
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
            // Save current state to context and export it to a default JSON file (ShoppingList.json)
            context.setData(List.of(listState)); 
            context.exportData("json"); 
            System.out.println("List successfully exported to: ShoppingList.json");
        } catch (IOException e) {
            System.out.println("Error exporting: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            // Import data from the default JSON file
            context.importData("json"); 
            List<ShoppingListState> data = context.getData();
            
            if (data != null && !data.isEmpty()) {
                listState = data.get(0);
                System.out.println("List successfully imported from ShoppingList.json!");
                System.out.println("Items loaded: " + listState.getItems().size());
            } else {
                System.out.println("Error: No valid data found to import.");
            }
        } catch (IOException e) {
            System.out.println("Error importing: " + e.getMessage());
        }
    }
}
```
## 5. Configuring the Runner
The runner is the application's entry point. It initializes the JMiniApp framework, registers the State class, the JSON Adapter, and sets the application's unique name, which the framework uses for managing the persistence file.

### A. ShoppingListAppRunner.java
Setting the name correctly is vital, as the framework uses this name to determine the checkpoint file (ShoppingList.json).

```bash
package com.jminiapp.examples.shoppinglist;

import com.jminiapp.core.engine.JMiniAppRunner;

public class ShoppingListAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(ShoppingListApp.class)
            .withState(ShoppingListState.class)
            .withAdapters(new ShoppingListJSONAdapter())
            // Setting the name ensures the checkpoint file is named consistently (e.g., ShoppingList.json)
            .named("ShoppingList") 
            .run(args);
    }
}
```
## 6. Compilation and Execution
### A. Compile the Project
From the examples/shopping-list directory, use the following PowerShell command to compile the project using Maven:
```bash
& "C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd" package
```
### B. Execute the Application
Run the application using the full classpath command. Ensure your terminal is inside the examples/shopping-list directory.

### C. Testing the Persistence
Add 2-3 items using Option 2.

Exit using Option 7 (this triggers the automatic saving via the shutdown() method).

Execute the application again using the same command.

The application should confirm "Lista cargada con X artículos", demonstrating successful persistence.