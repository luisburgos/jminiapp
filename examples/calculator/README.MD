# Calculator Example Application

A calculator with simple logic and state management.

## Features

- Application lifecycle (`initialize`, `run`, `shutdown`)
- Basic arithmetic operations: add, subtract, multiply, divide
- Result value is persistent between runs (using JSON)
- Console-based interactive menu

## Quick Start

- First you have to do a build of the aplication using the command: "mvn clean package"
- Then you need to direct yourself to the calculator folder using the command: "cd examples/calculator"
- And to execute the app you need to do: "mvn exec:java"

## Application Overview

The calculator allows you to perform basic arithmetic. It maintains a persistent result value. You can export/import the current calculator state to/from a JSON file.

## Example Run

The app will show you a welcome message with the basic operations

```bash
=== Welcome to Mini Calculator ===
Starting with new calculator. Result: 0.0

=== Mini Calculator ===
Current result: 0.0
Select the operation:
1. Add
2. Subtract
3. Multiply
4. Divide
5. Reset result to 0
6. Export to JSON file
7. Import from JSON file
8. Exit
Select the operation:
```

When you do an operation you can either work with the last result or with new numbers
```bash
=== Welcome to Mini Calculator ===
Starting with new calculator. Result: 0.0

=== Mini Calculator ===
Current result: 0.0
Select the operation:
1. Add
2. Subtract
3. Multiply
4. Divide
5. Reset result to 0
6. Export to JSON file
7. Import from JSON file
8. Exit
Select the operation: 1
Enter the first number (or 'r' to use previous result): 25
Enter the second number: 30
Result: 55.0

"Last Result case"
=== Mini Calculator ===
Current result: 55.0 
Select the operation:
1. Add
2. Subtract
3. Multiply
4. Divide
5. Reset result to 0
6. Export to JSON file
7. Import from JSON file
8. Exit
Select the operation: 2
Enter the first number (or 'r' to use previous result): r
Using previous result: 55.0
Enter the second number: 25
Result: 30.0

"Last result updates between operations"
=== Mini Calculator ===
Current result: 30.0
Select the operation:
1. Add
2. Subtract
3. Multiply
4. Divide
5. Reset result to 0
6. Export to JSON file
7. Import from JSON file
8. Exit
Select the operation:
```


## Key Classes

### MiniCalculatorModel

Contains the calculator logic and holds the result value:

```java
public class MiniCalculatorModel {
    private double result;

    public MiniCalculatorModel() {
        this.result = 0.0;
    }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public double add(double a, double b) {
        result = a + b;
        return result;
    }
    public double subtract(double a, double b) {
        result = a - b;
        return result;
    }
    public double multiply(double a, double b) {
        result = a * b;
        return result;
    }
    public double divide(double a, double b) {
        if (b == 0) throw new IllegalArgumentException("Division by zero.");
        result = a / b;
        return result;
    }
}
```

### MiniCalculatorApp

Manages the application lifecycle and user interaction, using the model and framework context for persistence.

### CalculatorJSONAdapter

Enables the application to serialize/deserialize the calculator state to and from JSON.

### MiniCalculatorAppRunner

Bootstraps the app

```java
public class MiniCalculatorAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(MiniCalculatorApp.class)
            .withState(MiniCalculatorModel.class)
            .withAdapters(new CalculatorJSONAdapter())
            .named("MiniCalculator")
            .run(args);
    }
}
```

## Author Information

Osmar Lopez Delgado


