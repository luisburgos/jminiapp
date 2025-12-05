package com.jminiapp.examples.flashcards;
import com.jminiapp.core.engine.JMiniAppRunner;

public class FlashcardAppRunner {
    public static void main(String[] args){
        JMiniAppRunner
                .forApp(FlashcardsApp.class)
                .named("Flashcards")
                .withState(Flashcards.class)
                .withAdapters(new FlashcardJSONAdapter())
                .run(args);
    }
}
