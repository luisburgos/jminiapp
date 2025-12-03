package com.jminiapp.examples.calculator;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.util.Scanner;


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
        model = new MiniCalculatorModel();
        scanner = new Scanner(System.in);
        running = true;
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
        scanner.close();
        System.out.println("\nClosing MiniCalculator...");
    }

    protected void displayMenu() {
        System.out.println("\n=== Mini Calculator ===");
        System.out.println("Select the operation:");
        System.out.println("1. Add");
        System.out.println("2. Subtract");
        System.out.println("3. Multiply");
        System.out.println("4. Divide");
        System.out.println("5. Exit");
        System.out.print("Select the operation: ");
    }

    protected void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();
            double number1, number2; 
    
            switch (input) {
                case "1":
                    System.out.println("Enter the first number: ");
                    number1 = scanner.nextDouble();
                    System.out.println("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    model.add(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;
    
                case "2":
                    System.out.println("Enter the first number: ");
                    number1 = scanner.nextDouble();
                    System.out.println("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    model.subtract(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;
    
                case "3":
                    System.out.println("Enter the first number: ");
                    number1 = scanner.nextDouble();
                    System.out.println("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    model.multiply(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;
    
                case "4":
                    System.out.println("Enter the first number: ");
                    number1 = scanner.nextDouble();
                    System.out.println("Enter the second number: ");
                    number2 = scanner.nextDouble();
                    model.divide(number1, number2);
                    System.out.println("Result: " + model.getResult());
                    break;
    
                case "5":
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
}