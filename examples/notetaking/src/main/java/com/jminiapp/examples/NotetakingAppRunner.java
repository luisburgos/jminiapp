package com.jminiapp.examples;

import com.jminiapp.core.engine.JMiniAppRunner;

public class NotetakingAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(NotetakingApp.class)
            .withState(NotetakingState.class)
            .withAdapters(new NotetakingJSONAdapter())
            //.withResourcesPath("test-data/")  // Custom import/export path
            .named("notetaking")
            .run(args);
    }
}