package com.jminiapp.examples;

import com.jminiapp.core.engine.JMiniAppRunner;

public class ContactsAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(ContactsApp.class)
            .withState(ContactsState.class)
            .withAdapters(new ContactsJSONAdapter())
            //.withResourcesPath("test-data/")  // Custom import/export path
            .named("Contacts")
            .run(args);
    }
}