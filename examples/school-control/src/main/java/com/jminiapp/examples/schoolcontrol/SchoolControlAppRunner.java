package com.jminiapp.examples.schoolcontrol;

import com.jminiapp.core.engine.JMiniAppRunner;

public class SchoolControlAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
                .forApp(SchoolControlApp.class)
                .withState(Student.class)
                .withAdapters(new SchoolControlJSONAdapter())
                .named("SchoolControl")
                .run(args);
    }
}