package com.example.finalproject;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String name;
    private String description;
    private double rating;
    private Address address;
    private double price; // Field to store the price of the restaurant

    // Updated constructor to include price
    public Restaurant(String name, String description, double rating, Address address, double price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
        this.price = price; // Properly initialize price
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for rating
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // Getter and Setter for address
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
