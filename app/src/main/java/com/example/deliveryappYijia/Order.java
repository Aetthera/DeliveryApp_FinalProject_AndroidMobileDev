package com.example.deliveryappYijia;

import java.util.List;

public class Order {
    private String id;
    private String items;

public Order(){}

    public Order(String id, String items) {
        this.id = id;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}

