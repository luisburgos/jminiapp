package com.jminiapp.examples.damagecalculator;

import java.util.ArrayList;
import java.util.List;

public class DamageState {

    private final List<Integer> history = new ArrayList<>();

    public int calculate(int atk, int def, double mult) {
        int raw = (int) ((atk - (def * 0.5)) * mult);
        int finalDamage = Math.max(raw, 0);
        history.add(finalDamage);
        return finalDamage;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void clear() {
        history.clear();
    }
}
