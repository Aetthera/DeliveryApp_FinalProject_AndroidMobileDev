package com.example.spayment;

public class PaymentRequest {
    private int amount; // Amount in the smallest currency unit (e.g., cents)

    // Constructor
    public PaymentRequest(int amount) {
        this.amount = amount;
    }

    // Getter for amount
    public int getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
