# Calculator Example Application

A calculator with simple logic and basic state management.

## Features

- Basic lifecycle (initialize, run, shutdown)
- Add, subtract, divide, and multiply functions
- Persistent state with the result
- Interactive menu system

## Quick Start

```bash
mvn clean package
cd examples/counter
mvn exec
```

## Code Snippets

### MiniCalculatorAppModel

This class is used to define the logic for operations of the calculator (like add, subtract, etc.). If you also want to add another type of operation, you should do the logic in this class.

**MiniCalculatorAppModel:**
```java
public class MiniCalculatorModel {
    private double result;

    public MiniCalculatorModel() {
        this.result = 0.0;
    }

    public MiniCalculatorModel(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double add(double number1, double number2) {
        result = number1 + number2;
        return result;
    }

    public double subtract(double number1, double number2) {
        result = number1 - number2;
        return result;
    }

    public double multiply(double number1, double number2) {
        result = number1 * number2;
        return result;
    }

    public double divide(double number1, double number2) {
        if (number2 == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        result = number1 / number2;
        return result;
    }

    public double addToResult(double number) {
        result += number;
        return result;
    }

    public double subtractFromResult(double number) {
        result -= number;
        return result;
    }

    public double multiplyResultBy(double number) {
        result *= number;
        return result;
    }

    public double divideResultBy(double number) {
        if (number == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        result /= number;
        return result;
    }

    public void reset() {
        this.result = 0.0;
    }

    @Override
    public String toString() {
        return "MiniCalculatorModel{result=" + result + "}";
    }
}
```

### MiniCalculatorApp

This class is the main application controller for the Mini Calculator. It manages the app lifecycle (initialization, operation, and shutdown), handles user interaction, persists state, and delegates calculation logic to the model.

**Application:**
```java
public class MiniCalculatorApp extends JMiniApp {

    private MiniCalculatorModel model;
    private Scanner scanner;
    private boolean running;

    public MiniCalculatorApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Welcome to Mini Calculator ===");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing calculator state from context
        List<MiniCalculatorModel> data = context.getData();
        if (data != null && !data.isEmpty()) {
            model = data.get(0);
            System.out.println("Loaded existing calculator state. Previous result: " + model.getResult());
        } else {
            model = new MiniCalculatorModel();
            System.out.println("Starting with new calculator. Result: " + model.getResult());
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
        // Save the calculator state to context
        List<MiniCalculatorModel> data = List.of(model);
        context.setData(data);

        scanner.close();
        System.out.println("\nFinal result: " + model.getResult());
        System.out.println("Closing MiniCalculator...");
    }

    protected void displayMenu() {
        System.out.println("\n=== Mini Calculator ===");
        System.out.println("Current result: " + model.getResult());
        System.out.println("Select the operation:");
        System.out.println("1. Add");
        System.out.println("2. Subtract");
        System.out.println("3. Multiply");
        System.out.println("4. Divide");
        System.out.println("5. Reset result to 0");
        System.out.println("6. Export to JSON file");
        System.out.println("7. Import from JSON file");
        System.out.println("8. Exit");
        System.out.print("Select the operation: ");
    }

    protected void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();
            double number1, number2;

            switch (input) {
                case "1":
                    System.out.print("Enter the first number (or 'r' to use previous result): ");
                    String input1 = scanner.nextLine().trim();
                    if (input1.equalsIgnoreCase("r")) {
                        number1 = model.getResult();
                        System.out.println("Using previous result: " + number1);
                    } else {
                        number1 = Double.parseDouble(input1);
                    }
                    System.out.print("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    scanner.nextLine();
                    model.add(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;

                case "2":
                    System.out.print("Enter the first number (or 'r' to use previous result): ");
                    input1 = scanner.nextLine().trim();
                    if (input1.equalsIgnoreCase("r")) {
                        number1 = model.getResult();
                        System.out.println("Using previous result: " + number1);
                    } else {
                        number1 = Double.parseDouble(input1);
                    }
                    System.out.print("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    scanner.nextLine();
                    model.subtract(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;

                case "3":
                    System.out.print("Enter the first number (or 'r' to use previous result): ");
                    input1 = scanner.nextLine().trim();
                    if (input1.equalsIgnoreCase("r")) {
                        number1 = model.getResult();
                        System.out.println("Using previous result: " + number1);
                    } else {
                        number1 = Double.parseDouble(input1);
                    }
                    System.out.print("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    scanner.nextLine();
                    model.multiply(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;

                case "4":
                    System.out.print("Enter the first number (or 'r' to use previous result): ");
                    input1 = scanner.nextLine().trim();
                    if (input1.equalsIgnoreCase("r")) {
                        number1 = model.getResult();
                        System.out.println("Using previous result: " + number1);
                    } else {
                        number1 = Double.parseDouble(input1);
                    }
                    System.out.print("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    scanner.nextLine();
                    model.divide(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;

                case "5":
                    model.reset();
                    System.out.println("Result reset to: " + model.getResult());
                    break;

                case "6":
                    exportToFile();
                    break;

                case "7":
                    importFromFile();
                    break;

                case "8":
                    running = false;
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void exportToFile() {
        try {
            context.setData(List.of(model));
            context.exportData("json");
            System.out.println("Calculator state exported successfully to: MiniCalculator.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");
            List<MiniCalculatorModel> data = context.getData();
            if (data != null && !data.isEmpty()) {
                model = data.get(0);
                System.out.println("Calculator state imported successfully from MiniCalculator.json!");
                System.out.println("New result: " + model.getResult());
            } else {
                System.out.println("Error: No data found in file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }
}
```

### CalculatorJSONAdapter

This class is a JSON adapter that allows the Mini Calculator application to serialize and deserialize its state (`MiniCalculatorModel`) to and from JSON format.

**JSONAdapter:**
```java
public class CalculatorJSONAdapter implements JSONAdapter<MiniCalculatorModel> {

    @Override
    public Class<MiniCalculatorModel> getstateClass() {
        return MiniCalculatorModel.class;
    }
}
```

### MiniCalculatorAppRunner

This class serves as the entry point and bootstrapper for the Mini Calculator application. Its main responsibilities are to register the application.

**Bootstrap:**
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