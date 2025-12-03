package com.jminiapp.examples.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jminiapp.persistence.PersistenceAdapter;

import java.io.File;
import java.io.IOException;

public class TodoJSONAdapter implements PersistenceAdapter<TodoState> {
    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public TodoJSONAdapter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(TodoState state) throws IOException {
        mapper.writeValue(new File(fileName), state);
    }

    @Override
    public TodoState load() throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return mapper.readValue(file, TodoState.class);
        }
        return new TodoState();
    }
}