package com.jminiapp.examples;

import com.jminiapp.core.engine.JMiniAppRunner;

public class FlashcardsAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(FlashcardsApp.class)
            .withState(FlashcardsState.class)
            .withAdapters(new FlashcardsJSONAdapter())
            //.withResourcesPath("test-data/")  // Custom import/export path
            .named("Flashcards")
            .run(args);
    }
}