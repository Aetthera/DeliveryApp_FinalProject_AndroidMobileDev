package com.example.spayment;

public class PaymentResponse {
    private String clientSecret;
    private String customer;
    private String ephemeralKey;

    // Getter for clientSecret
    public String getClientSecret() {
        return clientSecret;
    }

    // Setter for clientSecret (optional, if needed)
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    // Getter for customer
    public String getCustomer() {
        return customer;
    }

    // Setter for customer (optional, if needed)
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    // Getter for ephemeralKey
    public String getEphemeralKey() {
        return ephemeralKey;
    }

    // Setter for ephemeralKey (optional, if needed)
    public void setEphemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }
}
