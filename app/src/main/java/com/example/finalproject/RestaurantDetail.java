package com.example.finalproject;

import java.io.Serializable;
public class RestaurantDetail implements Serializable {
    private String name;
    private String Meal1;
    private String Meal2;
    private String Meal3;

    public RestaurantDetail( String name,String meal1, String meal2, String meal3) {
        this.name = name;

        this.Meal1 = meal1;
        this.Meal2 = meal2;
        this.Meal3 = meal3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeal1() {
        return Meal1;
    }

    public void setMeal1(String meal1) {
        this.Meal1 = meal1;
    }

    public String getMeal2() {
        return Meal2;
    }

    public void setMeal2(String meal2) {
        this.Meal2 = meal2;
    }

    public String getMeal3() {
        return Meal3;
    }

    public void setMeal3(String meal3) {
        this.Meal3 = meal3;
    }

}
