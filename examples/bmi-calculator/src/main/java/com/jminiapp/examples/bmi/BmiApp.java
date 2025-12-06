package com.jminiapp.examples.bmi;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * BMI (Body Mass Index) Calculator application using the JMiniApp framework.
 *
 * This app allows users to:
 * - Enter weight and height
 * - Calculate BMI automatically
 * - View BMI category (Underweight, Normal, Overweight, Obese)
 * - Save and load BMI data from JSON files
 * - Reset values
 * 
 * @author IvnVg04
 */
public class BmiApp extends JMiniApp {
    private Scanner scanner;
    private BmiState bmiState;
    private boolean running;

    public BmiApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     BMI CALCULATOR APPLICATION     ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("\nWelcome to the BMI Calculator!");
        System.out.println("Calculate your Body Mass Index and track your health.");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing BMI state from context
        List<BmiState> data = context.getData();
        if (data != null && !data.isEmpty()) {
            bmiState = data.get(0);
            System.out.println("\nLoaded existing BMI data:");
            displayBmiInfo();
        } else {
            bmiState = new BmiState();
            System.out.println("\nStarting with new BMI calculator");
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
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Thank you for using BMI Calculator!");
        System.out.println("Stay healthy!");
        System.out.println("=".repeat(40));
    }

    private void displayMenu() {
        System.out.println("\n" + "─".repeat(40));
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
                    System.out.println("\nExiting application...");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1-8.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void enterWeight() {
        System.out.print("\nEnter your weight in kilograms (e.g., 70.5): ");
        try {
            String input = scanner.nextLine().trim();
            double weight = Double.parseDouble(input);
            
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
        System.out.print("\nEnter your height in meters (e.g., 1.75): ");
        try {
            String input = scanner.nextLine().trim();
            double height = Double.parseDouble(input);
            
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
            System.out.println("\nPlease enter both weight and height first!");
            return;
        }

        double bmi = bmiState.calculateBmi();
        System.out.println("\n" + "═".repeat(40));
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
        System.out.println("\n" + "═".repeat(40));
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
        System.out.println("\nBMI Scale Reference:");
        System.out.println("  < 18.5    : Underweight");
        System.out.println("  18.5-24.9 : Normal weight");
        System.out.println("  25.0-29.9 : Overweight");
        System.out.println("  ≥ 30.0    : Obese");
    }

    private void resetValues() {
        bmiState.reset();
        System.out.println("\nAll values have been reset to 0");
    }

    private void exportToFile() {
        try {
            // Save current BMI state to context before exporting
            context.setData(List.of(bmiState));

            // Use default filename convention: {appName}.{format}
            context.exportData("json");
            System.out.println("\nBMI data exported successfully to: BmiCalculator.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            // Use default filename convention: {appName}.{format}
            context.importData("json");

            // Update local BMI state from context after import
            List<BmiState> data = context.getData();
            if (data != null && !data.isEmpty()) {
                bmiState = data.get(0);
                System.out.println("\nBMI data imported successfully from BmiCalculator.json!");
                displayBmiInfo();
            } else {
                System.out.println("Error: No data found in file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }
}
