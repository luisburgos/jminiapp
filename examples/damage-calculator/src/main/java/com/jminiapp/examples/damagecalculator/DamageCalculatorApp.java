package com.jminiapp.examples.damagecalculator;

import com.jminiapp.core.JMiniApp;
import com.jminiapp.core.JMiniAppRunner;

import java.io.IOException;
import java.util.Scanner;

public class DamageCalculatorApp extends JMiniApp {

    private DamageState state;

    @Override
    protected void initialize() {
        state = context.getState(DamageState.class);
        System.out.println("Damage Calculator App initialized.");
    }

    @Override
    protected void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== DAMAGE CALCULATOR ===");
            System.out.println("1. Calculate Damage");
            System.out.println("2. Show Damage History");
            System.out.println("3. Clear History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Attack value: ");
                    int atk = scanner.nextInt();

                    System.out.print("Defense value: ");
                    int def = scanner.nextInt();

                    System.out.print("Multiplier value: ");
                    double mult = scanner.nextDouble();

                    int damage = state.calculateDamage(atk, def, mult);
                    System.out.println("Final damage = " + damage);
                }
                case 2 -> System.out.println("History: " + state.getDamageHistory());
                case 3 -> {
                    state.clearHistory();
                    System.out.println("History cleared.");
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    @Override
    protected void shutdown() {
        context.setData(List.of(state));

        try {
            context.exportData("damage-calc");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }
