package com.jminiapp.examples.flashcards;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for FlashcardsState objects.
 *
 * <p>This adapter enables the Flashcards app to import and export flashcard state
 * to/from JSON files. It leverages the framework's JSONAdapter interface which
 * provides default implementations for serialization using Gson.</p>
 *
 * <p>Example JSON format:</p>
 * <pre>
 * [
 *   {
 *     "question": "What is the capital of France?",
 *     "answer": "Paris"
 *   }
 * ]
 * </pre>
 */
public class FlashcardsJSONAdapter implements JSONAdapter<FlashcardsState> {
    @Override
    public Class<FlashcardsState> getstateClass() { // Hot spot, except for the return whats inside can be changed
        return FlashcardsState.class;
    }
}