package com.jminiapp.examples.damagecalculator;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.util.Scanner;

public class DamageCalculatorApp extends JMiniApp {

    public DamageCalculatorApp(JMiniAppConfig config) {
        super(config);
    }

    private DamageState state;

    @Override
    protected void initialize() {
        state = new DamageState();
        System.out.println("=== Damage Calculator READY ===");
    }

    @Override
    protected void run() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Calculate damage");
            System.out.println("2. Show history");
            System.out.println("3. Clear");
            System.out.println("4. Exit");
            System.out.print("> ");

            int option = sc.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("Attack: ");
                    int atk = sc.nextInt();

                    System.out.print("Defense: ");
                    int def = sc.nextInt();

                    System.out.print("Multiplier: ");
                    double mult = sc.nextDouble();

                    int dmg = state.calculate(atk, def, mult);
                    System.out.println("Damage = " + dmg);
                }

                case 2 -> System.out.println("History: " + state.getHistory());

                case 3 -> {
                    state.clear();
                    System.out.println("History cleared.");
                }

                case 4 -> {
                    System.out.println("Bye!");
                    return; // ← Esto hace que run() termine y pase a shutdown()
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }

    @Override
    protected void shutdown() {
        // Aquí va la limpieza final
        System.out.println("\nShutting down Damage Calculator...");

        // Si quisieras guardar datos, aquí sería
        // state.saveHistory(context.getFile("history.txt"));

        System.out.println("Resources cleaned. See you next time!");
    }
}
