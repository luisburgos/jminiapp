package com.jminiapp.examples.daycare;

/**
 * Represents a single Pokemon with its stats and state.
 */
public class Pokemon {
    private String name;
    private int level;
    private int currentExp;
    private Long dropOffTimestamp; // Null if in party, Timestamp if in daycare

    // Empty constructor for JSON deserialization
    public Pokemon() {}

    public Pokemon(String name) {
        this.name = name;
        this.level = 1;
        this.currentExp = 0;
        this.dropOffTimestamp = null;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getCurrentExp() { return currentExp; }
    public Long getDropOffTimestamp() { return dropOffTimestamp; }

    public void setDropOffTimestamp(Long timestamp) {
        this.dropOffTimestamp = timestamp;
    }

    /**
     * Adds experience to the Pokemon and handles level ups.
     * @param amount The amount of experience to add.
     * @return True if the Pokemon reached max level (10), false otherwise.
     */
    public boolean addExperience(int amount) {
        if (level >= 10) return true;

        currentExp += amount;
        
        while (level < 10) {
            int reqExp = getRequiredExpForNextLevel(level);
            if (currentExp >= reqExp) {
                currentExp -= reqExp; // Reset exp, carrying over excess
                level++;
            } else {
                break;
            }
        }
        
        // If max level reached, cap exp at 0
        if (level >= 10) {
            currentExp = 0;
            return true;
        }
        return false;
    }

    /**
     * Returns the EXP required to go from currentLevel to currentLevel + 1.
     */
    public static int getRequiredExpForNextLevel(int currentLevel) {
        switch (currentLevel) {
            case 1: return 50;
            case 2: return 100;
            case 3: return 150;
            case 4: return 200;
            case 5: return 300;
            case 6: return 500;
            case 7: return 500;
            case 8: return 500;
            case 9: return 600;
            default: return 999999; // Should not happen if capped at 10
        }
    }
    
    @Override
    public String toString() {
        return name + " (Lvl " + level + ")";
    }
}