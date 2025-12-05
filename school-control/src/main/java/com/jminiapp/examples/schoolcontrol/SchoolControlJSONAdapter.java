package com.jminiapp.examples.schoolcontrol;

import com.jminiapp.core.adapters.JSONAdapter;

public class SchoolControlJSONAdapter implements JSONAdapter<Student> {
    @Override
    public Class<Student> getstateClass() {
        return Student.class;
    }
}