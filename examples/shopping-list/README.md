# 🛒 Shopping List MiniApp Example

This is a comprehensive example built using the JMiniApp framework that demonstrates key concepts for application persistence and user interaction through a command-line interface.

The application allows users to manage a simple list of items (add, remove, clear, view) and uses the framework's internal persistence mechanism (JSONAdapter) to automatically save and load the list state between sessions.

## Key Components

| Component | Description |
| :--- | :--- |
| `ShoppingListState.java` | Holds the application data (a list of strings) that is automatically persisted and recovered. |
| `ShoppingListJSONAdapter.java` | Provides the necessary class reference for the Gson library to handle serialization of the state object. |
| `ShoppingListApp.java` | Contains the core application logic, menu loop, and delegates persistence calls to the `JMiniApp` context. |
| `ShoppingListAppRunner.java` | The main entry point, registering the application, state, and adapter with the framework. |

## How to Run the Example

This example requires the main `jminiapp-core` module to be compiled first. You must execute these commands from the **`examples/shopping-list-example`** directory.

### 1. Compile the Project

Use Maven to compile the module and create the shaded JAR file containing all dependencies:

```powershell
& "C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd" package
```
### 2. Execute the MiniApp
Run the application using the generated JAR and specifying the classpath for the necessary dependencies (JMiniApp Core and Gson):
```bash
java -cp 'target/shopping-list-example-1.0.0-SNAPSHOT.jar;../../modules/core/target/jminiapp-core-1.0.0-SNAPSHOT.jar;C:\Users\TU_USUARIO\.m2\repository\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar' com.jminiapp.examples.shoppinglist.ShoppingListAppRunner
```
### 3. Testing Persistence
Add a few items using Option 2.

Exit the application using Option 7. This automatically saves the state to ShoppingList.json.

Re-run the application using the same command (Step 2).

The application will confirm: "List loaded with X items," verifying state persistence.