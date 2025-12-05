# BMI Calculator Example

A Body Mass Index (BMI) calculator application built with the JMiniApp framework. This example demonstrates how to create an interactive health application that calculates BMI based on user input and categorizes the result according to WHO standards.

## Overview

This example shows how to create a health-focused mini-app using JMiniApp core that calculates Body Mass Index (BMI) from weight and height measurements. The app provides BMI classification and data persistence features.

## Features

- **Calculate BMI**: Input weight (kg) and height (m) to calculate your Body Mass Index
- **BMI Classification**: Automatic categorization (Underweight, Normal, Overweight, Obese)
- **Data Persistence**: Save and load BMI data to/from JSON files
- **Data Reset**: Clear all values and start fresh
- **BMI Scale Reference**: View the BMI scale and understand your results

## What is BMI?

Body Mass Index (BMI) is a measurement that uses your height and weight to estimate body fat. It's calculated using the formula:

```
BMI = weight (kg) / height² (m²)
```

### BMI Categories (WHO Classification)

| BMI Range | Category |
|-----------|----------|
| < 18.5 | Underweight |
| 18.5 - 24.9 | Normal weight |
| 25.0 - 29.9 | Overweight |
| ≥ 30.0 | Obese |

## Project Structure

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
        │                   ├── BmiApp.java          # Main application logic
        │                   ├── BmiAppRunner.java    # Application entry point
        │                   ├── BmiState.java        # BMI data model
        │                   └── BmiJSONAdapter.java  # JSON serialization
        └── resources/
            └── BmiCalculator.json                   # Sample BMI data
```

## Key Components

### BmiState
The data model that stores:
- `weight` - Weight in kilograms
- `height` - Height in meters
- `bmi` - Calculated BMI value

Key methods:
- `calculateBmi()` - Computes BMI using the standard formula
- `getBmiCategory()` - Returns the WHO classification category
- `reset()` - Resets all values to zero

### BmiJSONAdapter
A format adapter that enables JSON import/export for `BmiState`:
- Implements `JSONAdapter<BmiState>` from the framework
- Registers with the framework during app bootstrap
- Provides automatic serialization/deserialization

### BmiApp
The main application class that extends `JMiniApp` and implements:
- `initialize()` - Set up the app and load existing BMI state
- `run()` - Main loop displaying menu and handling user input
- `shutdown()` - Save the BMI state before exiting
- Input validation for weight and height
- Formatted output with BMI results and categories

### BmiAppRunner
Bootstrap configuration that:
- Registers the `BmiJSONAdapter` with `.withAdapters()`
- Configures the app name as "BmiCalculator"
- Sets BmiState as the data model class
- Launches the application

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building and Running

### Build the project

From the **project root** (not the examples/bmi-calculator directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the bmi-calculator example.

### Run the application

**Option 1**: Using Maven exec plugin (from the examples/bmi-calculator directory)
```bash
cd examples/bmi-calculator
mvn exec:java
```

**Option 2**: From the project root
```bash
cd examples/bmi-calculator && mvn exec:java
```

**Option 3**: Using Maven from project root with module selection
```bash
mvn clean compile exec:java -pl examples/bmi-calculator
```

## How to Use

When you run the application, you'll see a menu with the following options:

1. **Enter Weight (kg)**: Input your weight in kilograms (e.g., 70.5)
2. **Enter Height (m)**: Input your height in meters (e.g., 1.75)
3. **Calculate BMI**: Compute your BMI and see your category
4. **View Current BMI Data**: Display your current stored values
5. **Reset All Values**: Clear all data and start over
6. **Export to JSON file**: Save your BMI data to `BmiCalculator.json`
7. **Import from JSON file**: Load BMI data from `BmiCalculator.json`
8. **Exit**: Close the application

## Usage Example

### Starting the Application

```
╔════════════════════════════════════╗
║     BMI CALCULATOR APPLICATION     ║
╚════════════════════════════════════╝

Welcome to the BMI Calculator!
Calculate your Body Mass Index and track your health.

Starting with new BMI calculator

────────────────────────────────────
MAIN MENU
────────────────────────────────────
1. Enter Weight (kg)
2. Enter Height (m)
3. Calculate BMI
4. View Current BMI Data
5. Reset All Values
6. Export to JSON file
7. Import from JSON file
8. Exit
────────────────────────────────────
Choose an option (1-8): 
```

### Entering Weight and Height

```
Choose an option (1-8): 1

Enter your weight in kilograms (e.g., 70.5): 70
Weight set to: 70.0 kg

────────────────────────────────────────
MAIN MENU
────────────────────────────────────────
Choose an option (1-8): 2

Enter your height in meters (e.g., 1.75): 1.75
Height set to: 1.75 m
```

### Calculating BMI

```
Choose an option (1-8): 3

════════════════════════════════════
BMI CALCULATION RESULT
════════════════════════════════════
Weight:    70.00 kg
Height:    1.75 m
BMI:       22.86
Category:  Normal weight
════════════════════════════════════

BMI Scale Reference:
  < 18.5    : Underweight
  18.5-24.9 : Normal weight
  25.0-29.9 : Overweight
  ≥ 30.0    : Obese
```

### Exporting Data

```
Choose an option (1-8): 6

BMI data exported successfully to: BmiCalculator.json
```

The exported JSON file will look like:
```json
[
  {
    "weight": 70.0,
    "height": 1.75,
    "bmi": 22.86
  }
]
```

### Importing Data

```
Choose an option (1-8): 7

BMI data imported successfully from BmiCalculator.json!

════════════════════════════════════
CURRENT BMI DATA
════════════════════════════════════
Weight:    70.00 kg
Height:    1.75 m
BMI:       22.86
Category:  Normal weight
════════════════════════════════════
```

## Learning Points

This example demonstrates:

1. **User Input Handling**: Reading and validating numeric input from users
2. **Mathematical Calculations**: Implementing the BMI formula
3. **Conditional Logic**: Categorizing BMI values into health categories
4. **Data Formatting**: Displaying formatted output with decimal precision
5. **Error Handling**: Managing invalid input gracefully
6. **State Management**: Persisting and loading application state with JMiniApp

## Next Steps

Try extending this example by:

- Adding support for different unit systems (imperial vs metric)
- Calculating and displaying ideal weight ranges
- Tracking BMI history over time with multiple records
- Adding BMI calculation for children and teens (different formulas)
- Implementing a BMI trend chart visualization
- Adding CSV export functionality using the `CSVAdapter`
- Creating a BMI goal tracker

## Author

Created by: **Ivan Vega**  
GitHub: [@IvnVg4](https://github.com/IvnVg4)  
Date: December 2025

## License

This example is part of the JMiniApp framework and follows the same license.
