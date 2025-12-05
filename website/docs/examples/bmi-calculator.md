---
sidebar_position: 3
---

# BMI Calculator

Learn how to build a Body Mass Index (BMI) calculator application using the JMiniApp framework. This tutorial will guide you through creating a health-focused app that calculates BMI, categorizes results, and persists data.

## What You'll Build

A complete BMI calculator application that:
- Accepts weight (kg) and height (m) as input
- Calculates BMI using the WHO formula
- Categorizes BMI results (Underweight, Normal, Overweight, Obese)
- Saves and loads data to/from JSON files
- Provides an interactive menu-driven interface

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Basic understanding of Java programming
- Familiarity with object-oriented programming concepts

## Step 1: Set Up the Project Structure

Create the following directory structure under `examples/`:

```
bmi-calculator/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── jminiapp/
        │           └── examples/
        │               └── bmi/
        │                   ├── BmiState.java
        │                   ├── BmiApp.java
        │                   ├── BmiAppRunner.java
        │                   └── BmiJSONAdapter.java
        └── resources/
            └── BmiCalculator.json
```

## Step 2: Configure the Maven POM

Create `pom.xml` in the `bmi-calculator` directory:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.jminiapp</groupId>
        <artifactId>jminiapp</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../../pom.xml</relativePath>
    </parent>

    <artifactId>bmi-calculator</artifactId>
    <packaging>jar</packaging>
    <name>BMI Calculator Example</name>

    <dependencies>
        <dependency>
            <groupId>com.jminiapp</groupId>
            <artifactId>jminiapp-core</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.jminiapp.examples.bmi.BmiAppRunner</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## Step 3: Create the Data Model (BmiState)

The `BmiState` class stores weight, height, and BMI values:

```java
package com.jminiapp.examples.bmi;

public class BmiState {
    private double weight; // Weight in kilograms
    private double height; // Height in meters
    private double bmi;    // Calculated BMI value

    public BmiState() {
        this.weight = 0.0;
        this.height = 0.0;
        this.bmi = 0.0;
    }

    public BmiState(double weight, double height) {
        this.weight = weight;
        this.height = height;
        this.bmi = calculateBmi();
    }

    // Getters and setters
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        this.bmi = calculateBmi();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        this.bmi = calculateBmi();
    }

    public double getBmi() {
        return bmi;
    }

    /**
     * Calculate BMI using the formula: weight / (height²)
     */
    public double calculateBmi() {
        if (height <= 0) {
            return 0.0;
        }
        this.bmi = weight / (height * height);
        return this.bmi;
    }

    /**
     * Get the BMI category based on WHO classification
     */
    public String getBmiCategory() {
        if (bmi == 0.0) {
            return "Not calculated";
        } else if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public void reset() {
        this.weight = 0.0;
        this.height = 0.0;
        this.bmi = 0.0;
    }

    @Override
    public String toString() {
        return String.format("BmiState{weight=%.2f kg, height=%.2f m, bmi=%.2f, category=%s}", 
                           weight, height, bmi, getBmiCategory());
    }
}
```

### Key Points:
- **Automatic calculation**: Setting weight or height automatically recalculates BMI
- **WHO classification**: `getBmiCategory()` returns the standard BMI category
- **Validation**: `calculateBmi()` checks for zero height to avoid division errors

## Step 4: Create the JSON Adapter

The `BmiJSONAdapter` enables JSON serialization:

```java
package com.jminiapp.examples.bmi;

import com.jminiapp.core.adapters.JSONAdapter;

public class BmiJSONAdapter implements JSONAdapter<BmiState> {

    @Override
    public Class<BmiState> getstateClass() {
        return BmiState.class;
    }
}
```

This adapter leverages the framework's default JSON implementation (Gson) for automatic serialization.

## Step 5: Implement the Main Application (BmiApp)

Create `BmiApp.java` - the heart of your application:

```java
package com.jminiapp.examples.bmi;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BmiApp extends JMiniApp {
    private Scanner scanner;
    private BmiState bmiState;
    private boolean running;

    public BmiApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\\n╔════════════════════════════════════╗");
        System.out.println("║     BMI CALCULATOR APPLICATION     ║");
        System.out.println("╚════════════════════════════════════╝");
        
        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing BMI state from context
        List<BmiState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            bmiState = data.get(0);
            System.out.println("\\nLoaded existing BMI data:");
            displayBmiInfo();
        } else {
            bmiState = new BmiState();
            System.out.println("\\nStarting with new BMI calculator");
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
        // Save the BMI state to context
        List<BmiState> data = List.of(bmiState);
        context.setData(data);

        scanner.close();
        System.out.println("\\nThank you for using BMI Calculator!");
        System.out.println("Stay healthy!");
    }

    private void displayMenu() {
        System.out.println("\\n" + "─".repeat(40));
        System.out.println("MAIN MENU");
        System.out.println("─".repeat(40));
        System.out.println("1. Enter Weight (kg)");
        System.out.println("2. Enter Height (m)");
        System.out.println("3. Calculate BMI");
        System.out.println("4. View Current BMI Data");
        System.out.println("5. Reset All Values");
        System.out.println("6. Export to JSON file");
        System.out.println("7. Import from JSON file");
        System.out.println("8. Exit");
        System.out.println("─".repeat(40));
        System.out.print("Choose an option (1-8): ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    enterWeight();
                    break;
                case "2":
                    enterHeight();
                    break;
                case "3":
                    calculateAndDisplayBmi();
                    break;
                case "4":
                    displayBmiInfo();
                    break;
                case "5":
                    resetValues();
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
                    System.out.println("Invalid option. Please choose 1-8.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void enterWeight() {
        System.out.print("\\nEnter your weight in kilograms (e.g., 70.5): ");
        try {
            double weight = Double.parseDouble(scanner.nextLine().trim());
            if (weight <= 0) {
                System.out.println("Weight must be greater than 0");
                return;
            }
            bmiState.setWeight(weight);
            System.out.println("Weight set to: " + weight + " kg");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void enterHeight() {
        System.out.print("\\nEnter your height in meters (e.g., 1.75): ");
        try {
            double height = Double.parseDouble(scanner.nextLine().trim());
            if (height <= 0) {
                System.out.println("Height must be greater than 0");
                return;
            }
            bmiState.setHeight(height);
            System.out.println("Height set to: " + height + " m");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void calculateAndDisplayBmi() {
        if (bmiState.getWeight() <= 0 || bmiState.getHeight() <= 0) {
            System.out.println("\\nPlease enter both weight and height first!");
            return;
        }

        double bmi = bmiState.calculateBmi();
        System.out.println("\\n" + "═".repeat(40));
        System.out.println("BMI CALCULATION RESULT");
        System.out.println("═".repeat(40));
        System.out.printf("Weight:    %.2f kg%n", bmiState.getWeight());
        System.out.printf("Height:    %.2f m%n", bmiState.getHeight());
        System.out.printf("BMI:       %.2f%n", bmi);
        System.out.printf("Category:  %s%n", bmiState.getBmiCategory());
        System.out.println("═".repeat(40));
        displayBmiScale();
    }

    private void displayBmiInfo() {
        System.out.println("\\n" + "═".repeat(40));
        System.out.println("CURRENT BMI DATA");
        System.out.println("═".repeat(40));
        
        if (bmiState.getWeight() <= 0 && bmiState.getHeight() <= 0) {
            System.out.println("No data entered yet.");
        } else {
            System.out.printf("Weight:    %.2f kg%n", bmiState.getWeight());
            System.out.printf("Height:    %.2f m%n", bmiState.getHeight());
            System.out.printf("BMI:       %.2f%n", bmiState.getBmi());
            System.out.printf("Category:  %s%n", bmiState.getBmiCategory());
        }
        System.out.println("═".repeat(40));
    }

    private void displayBmiScale() {
        System.out.println("\\nBMI Scale Reference:");
        System.out.println("  < 18.5    : Underweight");
        System.out.println("  18.5-24.9 : Normal weight");
        System.out.println("  25.0-29.9 : Overweight");
        System.out.println("  ≥ 30.0    : Obese");
    }

    private void resetValues() {
        bmiState.reset();
        System.out.println("\\nAll values have been reset to 0");
    }

    private void exportToFile() {
        try {
            context.setData(List.of(bmiState));
            context.exportData("json");
            System.out.println("\\nBMI data exported successfully to: BmiCalculator.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");
            List<BmiState> data = context.getData();
            if (data != null && !data.isEmpty()) {
                bmiState = data.get(0);
                System.out.println("\\nBMI data imported successfully!");
                displayBmiInfo();
            } else {
                System.out.println("Error: No data found in file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }
}
```

### Key Implementation Details:

1. **Lifecycle Methods**:
   - `initialize()`: Loads existing data or creates new state
   - `run()`: Main application loop
   - `shutdown()`: Saves state before exit

2. **Input Validation**: Both `enterWeight()` and `enterHeight()` validate that values are positive

3. **User Experience**: Clear visual feedback with emojis and formatted output

4. **Error Handling**: Try-catch blocks prevent crashes from invalid input

## Step 6: Create the Application Runner

Create `BmiAppRunner.java` to bootstrap the application:

```java
package com.jminiapp.examples.bmi;

import com.jminiapp.core.engine.JMiniAppRunner;

public class BmiAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(BmiApp.class)
            .withState(BmiState.class)
            .withAdapters(new BmiJSONAdapter())
            .named("BmiCalculator")
            .run(args);
    }
}
```

The runner configures:
- **forApp**: The main application class
- **withState**: The data model class
- **withAdapters**: JSON serialization adapter
- **named**: Application name (used for file naming)

## Step 7: Create Sample Data

Create `src/main/resources/BmiCalculator.json`:

```json
[
  {
    "weight": 70.0,
    "height": 1.75,
    "bmi": 22.86
  }
]
```

This provides sample data for testing the import functionality.

## Step 8: Build and Run

From the project root directory:

```bash
# Build the project
mvn clean install

# Run the BMI Calculator
cd examples/bmi-calculator
mvn exec:java
```

## Testing the Application

### Test Scenario 1: Normal Weight Calculation

1. Run the application
2. Choose option `1` and enter weight: `70`
3. Choose option `2` and enter height: `1.75`
4. Choose option `3` to calculate BMI
5. Expected result: BMI ≈ 22.86 (Normal weight)

### Test Scenario 2: Export and Import

1. Enter weight and height
2. Calculate BMI
3. Choose option `6` to export
4. Exit the application (option `8`)
5. Run the application again
6. Choose option `7` to import
7. Verify the data is restored correctly

### Test Scenario 3: Different BMI Categories

Try these examples to see different categories:

| Weight (kg) | Height (m) | Expected BMI | Category |
|-------------|------------|--------------|----------|
| 50 | 1.75 | 16.33 | Underweight |
| 70 | 1.75 | 22.86 | Normal |
| 85 | 1.75 | 27.76 | Overweight |
| 100 | 1.75 | 32.65 | Obese |

## Understanding the BMI Formula

The BMI calculation is straightforward:

```
BMI = weight (kg) / height² (m²)
```

Example:
```
Weight = 70 kg
Height = 1.75 m
BMI = 70 / (1.75 × 1.75) = 70 / 3.0625 = 22.86
```

## Extending the Application

Here are some ideas to enhance your BMI calculator:

### 1. Add Imperial Units Support

```java
private void enterWeightImperial() {
    System.out.print("Enter weight in pounds: ");
    double pounds = Double.parseDouble(scanner.nextLine());
    double kg = pounds * 0.453592; // Convert to kg
    bmiState.setWeight(kg);
}
```

### 2. Calculate Ideal Weight Range

```java
public String getIdealWeightRange() {
    double minWeight = 18.5 * (height * height);
    double maxWeight = 24.9 * (height * height);
    return String.format("%.1f - %.1f kg", minWeight, maxWeight);
}
```

### 3. BMI History Tracking

Modify `BmiState` to store multiple BMI records:

```java
public class BmiState {
    private List<BmiRecord> history;
    // ... existing fields
    
    public void addRecord(double weight, double height) {
        history.add(new BmiRecord(weight, height, LocalDate.now()));
    }
}
```

### 4. Add CSV Export

Implement `CSVAdapter` for spreadsheet compatibility:

```java
public class BmiCSVAdapter implements CSVAdapter<BmiState> {
    @Override
    public String[] getHeaders() {
        return new String[]{"Weight (kg)", "Height (m)", "BMI", "Category"};
    }
    
    @Override
    public String[] toCSVRecord(BmiState state) {
        return new String[]{
            String.valueOf(state.getWeight()),
            String.valueOf(state.getHeight()),
            String.valueOf(state.getBmi()),
            state.getBmiCategory()
        };
    }
}
```

## Common Issues and Solutions

### Issue: BMI shows 0.00

**Solution**: Make sure you enter both weight and height before calculating. The application requires both values.

### Issue: "Invalid input" error

**Solution**: Use decimal format with a dot (`.`) not comma (`,`). Example: `1.75` not `1,75`

### Issue: Import fails

**Solution**: Ensure `BmiCalculator.json` exists in the current directory and contains valid JSON format.

## Best Practices

1. **Input Validation**: Always validate user input before processing
2. **Error Handling**: Use try-catch blocks for robust error handling
3. **User Feedback**: Provide clear messages for all actions
4. **Data Persistence**: Save state on shutdown to preserve data
5. **Code Organization**: Separate concerns (data model, UI, business logic)

## Summary

You've successfully built a complete BMI Calculator application! You learned how to:

Create a data model with business logic  
 Implement input validation and error handling  
 Use the JMiniApp lifecycle methods effectively  
 Add JSON import/export functionality  
 Create an interactive menu-driven interface  
 Apply the WHO BMI classification standards  

## Next Steps

- Check out the [Counter Example](./counter.md) for a simpler introduction
- Explore the [API Documentation](../guides/jminiapp.md) for advanced features
- Learn about [Custom Adapters](../guides/custom-adapter.md) for different file formats

---

**Author**: Ivan Vega ([@IvnVg4](https://github.com/IvnVg4))  
**Date**: 05 December 2025
