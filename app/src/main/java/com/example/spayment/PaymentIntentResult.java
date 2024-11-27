package com.example.spayment;

import com.stripe.android.model.PaymentIntent;

public class PaymentIntentResult {
    private PaymentIntent paymentIntent;

    public PaymentIntentResult(PaymentIntent paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public PaymentIntent getIntent() {
        return paymentIntent;
    }
}
