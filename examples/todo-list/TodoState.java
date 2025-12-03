package com.jminiapp.examples.todo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TodoState implements Serializable {
    private List<String> tasks = new ArrayList<>();

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}