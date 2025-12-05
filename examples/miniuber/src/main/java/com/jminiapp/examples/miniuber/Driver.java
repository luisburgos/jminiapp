package com.jminiapp.examples.miniuber;

public class Driver {
    private String name;
    private double earnings;

    public Driver() {}

    public Driver(String name) {
        this.name = name;
        this.earnings = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getEarnings() {
        return earnings;
    }

    public void addEarnings(double amount) {
        earnings += amount;
    }

    @Override
    public String toString() {
        return name + " - Earnings: $" + earnings;
    }
}
