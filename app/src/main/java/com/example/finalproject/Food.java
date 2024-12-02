package com.example.finalproject;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;       // Firebase ID
    private String name;     // Food name
    private double price;    // Food price
    private String imageUrl; // Drawable name of the food image

    // Default constructor required for Firebase
    public Food() {
    }

    // Constructor for Food with name, price, and image URL
    public Food(String name, double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
