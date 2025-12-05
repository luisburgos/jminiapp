package com.jminiapp.examples.flashcards;

import com.jminiapp.core.adapters.JSONAdapter;

public class FlashcardJSONAdapter implements JSONAdapter<Flashcards> {
    @Override
    public Class<Flashcards> getstateClass() {
        return Flashcards.class;
    }
}