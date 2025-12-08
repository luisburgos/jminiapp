package com.jminiapp.examples.damagecalculator;

import java.util.ArrayList;
import java.util.List;

public class DamageState {

    private final List<Integer> damageHistory = new ArrayList<>();

    public int calculateDamage(int attack, int defense, double multiplier) {
        int rawDamage = (int) ((attack - (defense * 0.5)) * multiplier);
        int finalDamage = Math.max(rawDamage, 0);

        damageHistory.add(finalDamage);
        return finalDamage;
    }

    public List<Integer> getDamageHistory() {
        return damageHistory;
    }

    public void clearHistory() {
        damageHistory.clear();
    }
}
