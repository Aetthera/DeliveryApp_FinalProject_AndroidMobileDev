package com.example.finalproject;

import java.util.List;

public class DeliveryOrder {
    private String id;
    private List<String> foods; // List of food names
    private String deliveryAddress;
    private double totalAmount;
    private String status; // "Pending", "In Progress", "Delivered"

    public DeliveryOrder() {} // Default constructor for Firebase

    public DeliveryOrder(String id, List<String> foods, String deliveryAddress, double totalAmount, String status) {
        this.id = id;
        this.foods = foods;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
