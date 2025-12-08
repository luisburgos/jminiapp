package com.jminiapp.examples.shoppinglist;

import com.jminiapp.core.engine.JMiniAppRunner;

public class ShoppingListAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(ShoppingListApp.class)
            .withState(ShoppingListState.class)
            .withAdapters(new ShoppingListJSONAdapter())
            .named("ShoppingList")
            .run(args);
    }
}