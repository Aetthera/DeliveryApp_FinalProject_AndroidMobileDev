package com.example.finalproject;

import java.util.ArrayList;

public class Driver {

    public class Order {
        private ArrayList<String> cartItems;

        public Order() {
            // Default constructor
        }

        public Order(ArrayList<String> cartItems) {
            this.cartItems = cartItems;
        }

        public ArrayList<String> getCartItems() {
            return cartItems;
        }

        public void setCartItems(ArrayList<String> cartItems) {
            this.cartItems = cartItems;
        }

        public String getFoods() {
            if (cartItems == null || cartItems.isEmpty()) {
                return "No items";
            }
            return String.join(", ", cartItems);
        }
    }

}
