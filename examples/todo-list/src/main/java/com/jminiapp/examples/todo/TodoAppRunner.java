package com.jminiapp.examples.todo;

import com.jminiapp.core.engine.JMiniAppRunner;

public class TodoAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(TodoApp.class)              // Specifies the main application class.
            .withState(TodoItem.class)          // Registers the data model for persistence.
            .withAdapters(new TodoJSONAdapter()) // Registers the JSON adapter for file handling.
            .named("MyTodoList")                // Sets the internal name for persistence files.
            .run(args);                         // Starts the JMiniApp lifecycle.
    }
}