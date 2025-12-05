package com.jminiapp.examples.miniuber;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Mini Uber Application Example
 *
 * This app allows users to:
 * - Register drivers
 * - View total drivers registered
 */
public class MiniUberApp extends JMiniApp {

    private List<Driver> drivers;
    private boolean running;
    private Scanner scanner;

    public MiniUberApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Mini Uber App ===");
        drivers = context.getData();
        if (drivers == null) {
            drivers = new ArrayList<>();
        }
        scanner = new Scanner(System.in);
        running = true;
    }

    @Override
    protected void run() {
        while (running) {
            showMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        context.setData(drivers);
        scanner.close();
        System.out.println("\nSaving driver data...");
        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println("\nRegistered drivers: " + drivers.size());
        System.out.println("1. Register driver");
        System.out.println("2. Export drivers (JSON)");
        System.out.println("3. Import drivers (JSON)");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleUserInput() {
        try {
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> registerDriver();
                case "2" -> exportDrivers();
                case "3" -> importDrivers();
                case "4" -> running = false;
                default -> System.out.println("Invalid option. Please choose 1-4.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registerDriver() {
        System.out.print("Driver name: ");
        String name = scanner.nextLine();
        drivers.add(new Driver(name));
        System.out.println("Driver registered successfully!");
    }

    private void exportDrivers() {
        try {
            context.setData(drivers);
            context.exportData("json");
            System.out.println("Driver list exported!");
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    private void importDrivers() {
        try {
            context.importData("json");
            drivers = context.getData();
            System.out.println("Driver list imported!");
        } catch (IOException e) {
            System.out.println("Error importing data: " + e.getMessage());
        }
    }
}

