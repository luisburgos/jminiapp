package com.jminiapp.examples.daycare;

import java.util.ArrayList;
import java.util.List;

/**
 * Global state container for the application.
 * Stores configuration, trainer wallet, and all Pokemon.
 */
public class DaycareState {
    // Config
    private boolean isConfigured;
    private String daycareName;
    private int costPerMinute;
    private int expPerMinute;

    // Trainer Data
    private double trainerMoney;
    private List<Pokemon> party;
    private List<Pokemon> daycareStorage;

    public DaycareState() {
        // Default initialization
        this.isConfigured = false;
        this.trainerMoney = 100000.0;
        this.party = new ArrayList<>();
        this.daycareStorage = new ArrayList<>();
    }

    // Getters and Setters
    public boolean isConfigured() { return isConfigured; }
    public void setConfigured(boolean configured) { isConfigured = configured; }

    public String getDaycareName() { return daycareName; }
    public void setDaycareName(String daycareName) { this.daycareName = daycareName; }

    public int getCostPerMinute() { return costPerMinute; }
    public void setCostPerMinute(int costPerMinute) { this.costPerMinute = costPerMinute; }

    public int getExpPerMinute() { return expPerMinute; }
    public void setExpPerMinute(int expPerMinute) { this.expPerMinute = expPerMinute; }

    public double getTrainerMoney() { return trainerMoney; }
    public void setTrainerMoney(double trainerMoney) { this.trainerMoney = trainerMoney; }

    public List<Pokemon> getParty() { return party; }
    public void setParty(List<Pokemon> party) { this.party = party; }

    public List<Pokemon> getDaycareStorage() { return daycareStorage; }
    public void setDaycareStorage(List<Pokemon> daycareStorage) { this.daycareStorage = daycareStorage; }
    
    public void resetToDefaults() {
        this.isConfigured = false;
        this.daycareName = null;
        this.costPerMinute = 0;
        this.expPerMinute = 0;
        this.trainerMoney = 100000.0;
        
        this.party.clear();
        this.party.add(new Pokemon("Bulbasaur"));
        this.party.add(new Pokemon("Charmander"));
        this.party.add(new Pokemon("Squirtle"));
        
        this.daycareStorage.clear();
    }
}