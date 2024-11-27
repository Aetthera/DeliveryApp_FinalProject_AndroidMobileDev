package com.example.spayment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";
    private static final String BASE_URL = "http://192.168.2.59:4242/"; // Flask backend
    private static final String PUBLISHABLE_KEY = "pk_test_51QLRum029qv9sJ0B52sonzCCgCvtbqAuM5XxWjTJhiQFCxDnVb3N15IDsb1VNV2uRVHkRDCis1N8NH8mfsA0mimR005UesyBCZ";

    private Stripe stripe;
    private String clientSecret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize Stripe SDK
        PaymentConfiguration.init(this, PUBLISHABLE_KEY);
        stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());

        // Set up the CardInputWidget
        CardInputWidget cardInputWidget = findViewById(R.id.card_input_widget);

        // Set up the Pay button
        Button payButton = findViewById(R.id.pay_button);
        payButton.setOnClickListener(v -> {
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                confirmPayment(params);
            } else {
                Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show();
            }
        });

        // Start the payment process by fetching the clientSecret
        startPayment(5000); // Example: $50.00 CAD
    }

    private void startPayment(int amount) {
        PaymentApi api = RetrofitClient.getInstance(BASE_URL).create(PaymentApi.class);
        PaymentRequest request = new PaymentRequest(amount);

        // Make API call to create PaymentIntent
        api.createPaymentIntent(request).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clientSecret = response.body().getClientSecret();
                    Log.d(TAG, "Client Secret: " + clientSecret);
                } else {
                    Log.e(TAG, "Failed to create PaymentIntent: " + response.message());
                    Toast.makeText(PaymentActivity.this, "Error creating payment intent", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Log.e(TAG, "Network error: " + t.getMessage());
                Toast.makeText(PaymentActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmPayment(PaymentMethodCreateParams params) {
        if (clientSecret != null) {
            // Create ConfirmPaymentIntentParams with the clientSecret
            ConfirmPaymentIntentParams confirmParams =
                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, clientSecret);

            // Confirm the payment and display results directly
            stripe.confirmPayment(this, confirmParams);

            Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Client Secret is null!");
            Toast.makeText(this, "Error: Client Secret is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void showResultDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}

//package com.example.spayment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.stripe.android.PaymentConfiguration;
//import com.stripe.android.Stripe;
//import com.stripe.android.model.ConfirmPaymentIntentParams;
//import com.stripe.android.model.PaymentIntent;
//import com.stripe.android.model.PaymentMethodCreateParams;
//import com.stripe.android.view.CardInputWidget;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class PaymentActivity extends AppCompatActivity {
//
//    private static final String TAG = "PaymentActivity";
//    private static final String BASE_URL = "http://192.168.2.59:4242/"; // Flask backend
//    private static final String PUBLISHABLE_KEY = "pk_test_51QLRum029qv9sJ0B52sonzCCgCvtbqAuM5XxWjTJhiQFCxDnVb3N15IDsb1VNV2uRVHkRDCis1N8NH8mfsA0mimR005UesyBCZ";
//
//    private Stripe stripe;
//    private String clientSecret;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
//
//        // Initialize Stripe SDK
//        PaymentConfiguration.init(this, PUBLISHABLE_KEY);
//        stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());
//
//        // Set up the CardInputWidget
//        CardInputWidget cardInputWidget = findViewById(R.id.card_input_widget);
//
//        // Set up the Pay button
//        Button payButton = findViewById(R.id.pay_button);
//        payButton.setOnClickListener(v -> {
//            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
//            if (params != null) {
//                confirmPayment(params);
//            } else {
//                Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        startPayment(5000); // Example: $50.00 CAD
//    }
//
//    private void startPayment(int amount) {
//        PaymentApi api = RetrofitClient.getInstance(BASE_URL).create(PaymentApi.class);
//        PaymentRequest request = new PaymentRequest(amount);
//
//        // Make API call to create PaymentIntent
//        api.createPaymentIntent(request).enqueue(new Callback<PaymentResponse>() {
//            @Override
//            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    clientSecret = response.body().getClientSecret();
//                    Log.d(TAG, "Client Secret: " + clientSecret);
//                } else {
//                    Log.e(TAG, "Failed to create PaymentIntent: " + response.message());
//                    Toast.makeText(PaymentActivity.this, "Error creating payment intent", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PaymentResponse> call, Throwable t) {
//                Log.e(TAG, "Network error: " + t.getMessage());
//                Toast.makeText(PaymentActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void confirmPayment(PaymentMethodCreateParams params) {
//        if (clientSecret != null) {
//            // Create ConfirmPaymentIntentParams with the clientSecret
//            ConfirmPaymentIntentParams confirmParams =
//                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, clientSecret);
//
//            // Confirm the payment and handle response
//            stripe.confirmPayment(this, confirmParams);
//
//            Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e(TAG, "Client Secret is null!");
//            Toast.makeText(this, "Error: Client Secret is not available", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
////    private void handlePaymentFailure() {
////        try {
////            Toast.makeText(PaymentActivity.this, "Payment failed. Please try again.", Toast.LENGTH_SHORT).show();
////            Intent intent = new Intent(PaymentActivity.this, ErrorActivity.class);
////            startActivity(intent);
////            finish(); // Close PaymentActivity
////        } catch (Exception e) {
////            Log.e("PaymentActivity", "Error navigating to ErrorActivity: " + e.getMessage());
////        }
////    }
//
//
//
//}
