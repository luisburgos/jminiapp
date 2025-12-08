package com.jminiapp.examples.flashcards;

import com.jminiapp.core.engine.JMiniAppRunner;

public class FlashcardsAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner // Hot spot, launching the app with its configuration depending on the app
            .forApp(FlashcardsApp.class) // Value changes based on the app
            .withState(FlashcardsState.class) // Value changes based on the app
            .withAdapters(new FlashcardsJSONAdapter()) // Value changes based on the app
            //.withResourcesPath("test-data/") // Value changes based on the app
            .named("Flashcards") // Value changes based on the app
            .run(args);
    }
}