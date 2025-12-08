package com.jminiapp.examples.shoppinglist;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListState {
    private List<String> items;

    public ShoppingListState() {
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        this.items.add(item);
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void clear() {
        this.items.clear();
    }

    @Override
    public String toString() {
        return "ShoppingListState{items=" + items.size() + "}";
    }
}