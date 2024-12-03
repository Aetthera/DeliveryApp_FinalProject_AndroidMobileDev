package com.example.finalproject;

import java.io.Serializable;

public class Address implements Serializable {
    private String houseNumber;
    private String streetName;
    private String apartment;
    private String zipcode;


    public Address(String houseNumber, String streetName, String apartment, String zipcode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.apartment = apartment;
        this.zipcode = zipcode;
    }

    public Address(String houseNumber, String streetName, String zipcode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.apartment = null;
        this.zipcode = zipcode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
