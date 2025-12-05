package com.jminiapp.examples.flashcards;

public class Flashcards {

    private String question;
    private String answer;
    private boolean isMemorized;

    public Flashcards() {}

    public Flashcards(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.isMemorized = false;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isMemorized() {
        return isMemorized;
    }

    public void setMemorized(boolean memorized) {
        isMemorized = memorized;
    }
    @Override
    public String toString() {
        return String.format("[%s] Q: %s", (isMemorized ? "X" : " "), question);
    }
}
