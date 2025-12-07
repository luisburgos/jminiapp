package com.jminiapp.examples.expensetracker;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * Adapter to handle JSON serialization for Expense objects.
 */
public class ExpenseJSONAdapter implements JSONAdapter<Expense> {
    @Override
    public Class<Expense> getstateClass() {
        return Expense.class;
    }
}