package com.example.finalproject;

import java.io.Serializable;

public class Reward implements Serializable {
    private String id;        // Reward ID
    private String name;      // Reward name
    private String description; // Reward description
    private int pointsRequired; // Points required to redeem

    public Reward() {
        // Default constructor for Firebase
    }

    public Reward(String name, String description, int pointsRequired) {
        this.name = name;
        this.description = description;
        this.pointsRequired = pointsRequired;
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

    public String getDescription() {
        return description;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }
}
