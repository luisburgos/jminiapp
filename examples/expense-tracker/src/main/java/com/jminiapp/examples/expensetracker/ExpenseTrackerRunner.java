package com.jminiapp.examples.expensetracker;

import com.jminiapp.core.engine.JMiniAppRunner;

public class ExpenseTrackerRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(ExpenseTrackerApp.class)
            .withState(Expense.class)
            .withAdapters(new ExpenseJSONAdapter())
            .named("ExpenseTracker")
            .run(args);
    }
}