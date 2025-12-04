package com.example.todolist;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for Task objects.
 *
 * Enables automatic serialization/deserialization of Task objects to/from JSON files.
 */
public class TaskJSONAdapter implements JSONAdapter<Task> {

    @Override
    public Class<Task> getStateClass() {
        return Task.class;
    }
}
