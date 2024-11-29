package com.example.deliveryapp_finalproject_androidmobiledev;

import java.util.ArrayList;

public class Order {
    private ArrayList<String> cartItems;

//    public Order() {
//        // Default constructor
//    }

    public Order(ArrayList<String> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<String> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<String> cartItems) {
        this.cartItems = cartItems;
    }
}

