package com.jminiapp.examples.contacts;

/**
 * Simple model representing a contact with a name and phone number.
 *
 * This is used by the ContactsApp to store the current contact state.
 */

public class ContactsState {
    private String name;
    private String phoneNumber;

    public ContactsState() {
        this("", "");
    }

    public ContactsState(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}