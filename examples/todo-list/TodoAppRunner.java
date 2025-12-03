package com.jminiapp.examples.todo;

public class TodoAppRunner {
    public static void main(String[] args) {
        TodoJSONAdapter adapter = new TodoJSONAdapter("todo-state.json");
        TodoApp app = new TodoApp(adapter);
        app.run();
    }
}