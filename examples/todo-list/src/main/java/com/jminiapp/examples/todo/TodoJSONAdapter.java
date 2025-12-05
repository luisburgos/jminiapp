package com.jminiapp.examples.todo;

import com.jminiapp.core.adapters.JSONAdapter;

public class TodoJSONAdapter implements JSONAdapter<TodoItem> {

    /**
     * Required method to tell the framework the class type of the state model.
     * @return The class object for TodoItem.
     */
    @Override
    public Class<TodoItem> getstateClass() {
        return TodoItem.class;
    }
}