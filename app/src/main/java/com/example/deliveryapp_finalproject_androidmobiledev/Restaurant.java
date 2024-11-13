package com.example.deliveryapp_finalproject_androidmobiledev;

public class Restaurant {
    private String name;
    private String description;
    private double rating;
    private Address address;

    public Restaurant(String name, String description, double rating, Address address) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
