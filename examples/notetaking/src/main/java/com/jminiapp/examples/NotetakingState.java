package com.jminiapp.examples;

/**
 * Simple model representing a note with a title and content.
 *
 * This is used by the NotetakingApp to store the current note state.
 */
public class NotetakingState {
    private String title;
    private String content;

    public NotetakingState(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void clear() {
        this.title = "";
        this.content = "";
    }
}