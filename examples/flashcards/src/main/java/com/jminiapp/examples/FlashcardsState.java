package com.jminiapp.examples;

/**
 * Simple model representing a flashcard with a question and answer.
 *
 * This is used by the FlashcardsApp to store the current flashcard state.
 */

public class FlashcardsState {
    private String question;
    private String answer;

    public FlashcardsState() {
        this("", "");
    }

    public FlashcardsState(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}