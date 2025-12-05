    package com.jminiapp.examples.daycare;

    import com.jminiapp.core.api.JMiniApp;
    import com.jminiapp.core.api.JMiniAppConfig;

    import java.util.Collections;
    import java.util.List;
    import java.util.Scanner;

    public class DaycareApp extends JMiniApp {
        private DaycareState state;
        private Scanner scanner;
        private boolean isRunning;

        public DaycareApp(JMiniAppConfig config) {
            super(config);
        }

        @Override
        protected void initialize() {
            scanner = new Scanner(System.in);
            List<DaycareState> loadedData = null;

            try {
                // Explicit attempt to load from file for persistence between runs
                context.importData("DaycareData.json", "json"); 
                loadedData = context.getData();

            } catch (Exception e) {
                System.err.println("Persistence Warning: Could not load DaycareData.json. Starting fresh or with defaults.");
            }

            if (loadedData != null && !loadedData.isEmpty()) {
                state = loadedData.get(0);
                System.out.println("State loaded successfully. Welcome back!");
            } else {
                state = new DaycareState();
                state.resetToDefaults();
                System.out.println("Initializing new game state...");
            }

            isRunning = true;
        }

        @Override
        protected void run() {
            while (isRunning) {
                // First time setup check
                if (!state.isConfigured()) {
                    runInitialSetup();
                }

                // Main Menu
                clearScreen();
                System.out.println("==========================================");
                System.out.println("   " + state.getDaycareName() + " - Main Menu");
                System.out.println("==========================================");
                System.out.printf(" Wallet: %.2f $\n", state.getTrainerMoney());
                System.out.println("------------------------------------------");
                System.out.println("1. Leave Pokemon");
                System.out.println("2. Pick up Pokemon");
                System.out.println("3. Daycare Configuration");
                System.out.println("4. Reset System");
                System.out.println("5. Exit");
                System.out.println("------------------------------------------");
                System.out.print("Select an option: ");

                String input = scanner.nextLine().trim();
                handleMenuSelection(input);
            }
        }

        @Override
        protected void shutdown() {
            System.out.println("Saving game state...");
            context.setData(Collections.singletonList(state));
            // Auto-export to file for persistence between runs
            try {
                context.exportData("DaycareData.json", "json");
                System.out.println("State saved to DaycareData.json.");
            } catch (Exception e) {
                System.out.println("Error auto-saving: " + e.getMessage());
            }
            System.out.println("Goodbye, Trainer!");
            scanner.close();
        }

        // --- Logic Methods ---

        private void runInitialSetup() {
            clearScreen();
            System.out.println(">>> DAYCARE INITIAL CONFIGURATION <<<");
            System.out.print("Enter Daycare Name: ");
            state.setDaycareName(scanner.nextLine());

            System.out.print("Enter Cost per Minute ($): ");
            state.setCostPerMinute(readIntInput());

            System.out.print("Enter Exp Growth per Minute: ");
            state.setExpPerMinute(readIntInput());

            state.setConfigured(true);
            System.out.println("Configuration saved! Press Enter to continue...");
            scanner.nextLine();
        }

        private void handleMenuSelection(String input) {
            switch (input) {
                case "1": leavePokemon(); break;
                case "2": pickUpPokemon(); break;
                case "3": runInitialSetup(); break;
                case "4": 
                    state.resetToDefaults(); 
                    System.out.println("System reset complete!");
                    pressEnterToContinue();
                    break;
                case "5": isRunning = false; break;
                default: System.out.println("Invalid option."); pressEnterToContinue();
            }
        }

        private void leavePokemon() {
            clearScreen();
            System.out.println("--- YOUR PARTY ---");
            List<Pokemon> party = state.getParty();
            
            if (party.isEmpty()) {
                System.out.println("You have no Pokemon in your party!");
                pressEnterToContinue();
                return;
            }

            for (int i = 0; i < party.size(); i++) {
                Pokemon p = party.get(i);
                System.out.println((i + 1) + ". " + p + " [Exp: " + p.getCurrentExp() + "]");
            }
            System.out.println("0. Cancel");
            System.out.print("Select Pokemon to leave: ");

            int selection = readIntInput();
            if (selection > 0 && selection <= party.size()) {
                Pokemon selected = party.remove(selection - 1);
                selected.setDropOffTimestamp(System.currentTimeMillis());
                state.getDaycareStorage().add(selected);
                System.out.println("You left " + selected.getName() + " at the daycare.");
            }
            pressEnterToContinue();
        }

        private void pickUpPokemon() {
            clearScreen();
            System.out.println("--- DAYCARE STORAGE ---");
            List<Pokemon> storage = state.getDaycareStorage();

            if (storage.isEmpty()) {
                System.out.println("No Pokemon in daycare.");
                pressEnterToContinue();
                return;
            }

            long now = System.currentTimeMillis();

            for (int i = 0; i < storage.size(); i++) {
                Pokemon p = storage.get(i);
                long minutes = (now - p.getDropOffTimestamp()) / 60000;

                // We calculate both the cost and the new level to display it
                Projection proj = calculateProjection(p, minutes);
                
                System.out.println((i + 1) + ". " + p.getName() + " (Actual Lvl: " + proj.level + ")");
                System.out.printf("   Time: %d min | Cost: %.2f $%n", minutes, proj.cost);
            }
            System.out.println("0. Cancel");
            System.out.print("Select Pokemon to pick up: ");

            int selection = readIntInput();
            if (selection > 0 && selection <= storage.size()) {
                Pokemon p = storage.get(selection - 1);
                long minutes = (now - p.getDropOffTimestamp()) / 60000;

                double totalCost = processGrowth(p, minutes); // Calculates final cost and applies EXP

                if (state.getTrainerMoney() >= totalCost) {
                    state.setTrainerMoney(state.getTrainerMoney() - totalCost);
                    p.setDropOffTimestamp(null); // Reset time
                    storage.remove(p);
                    state.getParty().add(p);
                    
                    System.out.println("Success! " + p.getName() + " is back in your party.");
                    System.out.println("New Level: " + p.getLevel());
                    System.out.printf("Paid: %.2f $%n", totalCost);
                } else {
                    System.out.printf("Not enough money! You need %.2f $.%n", totalCost);
                }
            }
            pressEnterToContinue();
        }

        /**
        * Calculates growth step-by-step to enforce the "cap at level 10" rule
        * regarding cost. Cost stops increasing when Pokemon hits level 10.
        */
        private double processGrowth(Pokemon p, long minutes) {
            double totalCost = 0;
            int expRate = state.getExpPerMinute();
            int costRate = state.getCostPerMinute();

            // Simulate minute by minute (or calculate steps)
            // Optimization: Calculate time to next level
            long remainingMinutes = minutes;

            while (remainingMinutes > 0 && p.getLevel() < 10) {
                int expNeeded = Pokemon.getRequiredExpForNextLevel(p.getLevel()) - p.getCurrentExp();
                // How many minutes to get this exp?
                // Use Math.ceil to ensure we pay for the full minute needed to gain the exp
                long minsToLevel = (long) Math.ceil((double) expNeeded / expRate);

                if (remainingMinutes >= minsToLevel) {
                    // Apply full step
                    p.addExperience((int) (minsToLevel * expRate)); // Will trigger level up
                    totalCost += minsToLevel * costRate;
                    remainingMinutes -= minsToLevel;
                } else {
                    // Not enough time to level up
                    p.addExperience((int) (remainingMinutes * expRate));
                    totalCost += remainingMinutes * costRate;
                    remainingMinutes = 0;
                }
            }

            // Note: If Pokemon is already level 10 or reaches it, we stop adding cost.
            return totalCost;
        }

        /**
        * Estimates cost and level without modifying the real Pokemon.
        * Returns a Projection object.
        */
        private Projection calculateProjection(Pokemon original, long minutes) {
            int currentLvl = original.getLevel();
            int currentExp = original.getCurrentExp();
            double estimatedCost = 0;
            long remMin = minutes;
            
            while (remMin > 0 && currentLvl < 10) {
                int req = Pokemon.getRequiredExpForNextLevel(currentLvl) - currentExp;
                long minsToLvl = (long) Math.ceil((double) req / state.getExpPerMinute());
                
                if (remMin >= minsToLvl) {
                    estimatedCost += minsToLvl * state.getCostPerMinute();
                    remMin -= minsToLvl;
                    currentLvl++;
                    currentExp = 0;
                } else {
                    estimatedCost += remMin * state.getCostPerMinute();
                    remMin = 0;
                }
            }
            return new Projection(estimatedCost, currentLvl);
        }
        
        // Simple helper class to store projection result
        private static class Projection {
            double cost;
            int level;
            public Projection(double cost, int level) {
                this.cost = cost;
                this.level = level;
            }
        }

        // --- Helpers ---
        
        private void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        private int readIntInput() {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        
        private void pressEnterToContinue() {
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }
    }