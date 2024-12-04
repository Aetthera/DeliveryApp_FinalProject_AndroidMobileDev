package com.example.finalproject;

public class Driver {
    private String id;              // Unique ID for the driver
    private String name;            // Driver's name
    private String phoneNumber;     // Driver's phone number
    private String currentOrderId;  // Current order assigned to the driver

    public Driver() {} // Default constructor for Firebase

    public Driver(String id, String name, String phoneNumber, String currentOrderId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentOrderId = currentOrderId;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(String currentOrderId) {
        this.currentOrderId = currentOrderId;
    }
}
