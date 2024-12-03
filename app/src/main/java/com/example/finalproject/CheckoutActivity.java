package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    private double cartTotal;
    private double tipAmount = 0.0;
    private TextView totalPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Retrieve cart total from Intent
        cartTotal = getIntent().getDoubleExtra("cart_total", 0.0);

        TextView cartTotalText = findViewById(R.id.cart_total_text);
        totalPriceText = findViewById(R.id.total_price_text);
        RadioGroup tipGroup = findViewById(R.id.tip_options_group);
        EditText customTipInput = findViewById(R.id.custom_tip_input);
        EditText addressInput = findViewById(R.id.address_input);
        Button confirmButton = findViewById(R.id.pay_button);

        // Display cart total
        cartTotalText.setText(String.format("Cart Total: $%.2f", cartTotal));
        updateTotalPrice(); // Initialize total price

        // Handle Tip Selection
        tipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.tip_10) {
                tipAmount = cartTotal * 0.10;
            } else if (checkedId == R.id.tip_15) {
                tipAmount = cartTotal * 0.15;
            } else if (checkedId == R.id.tip_30) {
                tipAmount = cartTotal * 0.30;
            }
            updateTotalPrice(); // Update total price whenever the tip changes
        });

        // Handle Custom Tip Input
        customTipInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                try {
                    tipAmount = Double.parseDouble(customTipInput.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid custom tip amount", Toast.LENGTH_SHORT).show();
                    tipAmount = 0.0;
                }
                updateTotalPrice(); // Update total price when custom tip is entered
            }
        });

        // Handle Payment Button Click
        confirmButton.setOnClickListener(v -> {
            Log.d("CheckoutActivity", "Pay button clicked");

            String address = addressInput.getText().toString();
            if (address.isEmpty()) {
                Log.d("CheckoutActivity", "Address is empty");
                Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalAmount = cartTotal + tipAmount;

            // Save transaction to Firebase
            saveTransactionToFirebase(address, totalAmount);

            // Show order confirmation popup
            showConfirmationPopup(totalAmount, address);
        });
    }

    private void updateTotalPrice() {
        double totalAmount = cartTotal + tipAmount;
        totalPriceText.setText(String.format("Total: $%.2f", totalAmount));
    }

    private void saveTransactionToFirebase(String address, double totalAmount) {
        // Reference the "transactions" node in the database
        DatabaseReference transactionsRef = FirebaseDatabase.getInstance().getReference("transactions");

        // Generate a unique transaction ID
        String transactionId = transactionsRef.push().getKey();
        if (transactionId != null) {
            // Prepare data to save
            Map<String, Object> transactionData = new HashMap<>();
            transactionData.put("foods", "Pizza, Pasta"); // Example: Replace with actual selected items if available
            transactionData.put("total", totalAmount);
            transactionData.put("address", address);

            // Save the data to the "transactions" node
            transactionsRef.child(transactionId).setValue(transactionData)
                    .addOnSuccessListener(aVoid -> Log.d("CheckoutActivity", "Transaction saved successfully to Firebase."))
                    .addOnFailureListener(e -> Log.e("CheckoutActivity", "Failed to save transaction to Firebase: " + e.getMessage()));
        }
    }

    private void showConfirmationPopup(double totalAmount, String address) {
        Log.d("CheckoutActivity", "Inside showConfirmationPopup");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Confirmation");

        // Create a custom layout for the dialog
        LinearLayout popupLayout = new LinearLayout(this);
        popupLayout.setOrientation(LinearLayout.VERTICAL);
        popupLayout.setPadding(50, 30, 50, 30);

        // Create TextViews for content
        TextView titleText = new TextView(this);
        titleText.setText("Thank you for your order!");
        titleText.setTextSize(18);
        titleText.setTextColor(getResources().getColor(R.color.black));
        titleText.setPadding(0, 0, 0, 20);

        TextView foodsText = new TextView(this);
        foodsText.setText("Foods: Pizza, Pasta"); // Replace with dynamic items if necessary
        foodsText.setTextSize(16);
        foodsText.setTextColor(getResources().getColor(R.color.primary_background));

        TextView totalText = new TextView(this);
        totalText.setText(String.format("Total Paid: $%.2f", totalAmount));
        totalText.setTextSize(16);
        totalText.setTextColor(getResources().getColor(R.color.primary_background));
        totalText.setPadding(0, 10, 0, 10);

        TextView addressText = new TextView(this);
        addressText.setText(String.format("Delivery Address: %s", address));
        addressText.setTextSize(16);
        addressText.setTextColor(getResources().getColor(R.color.primary_background));

        TextView tipText = new TextView(this);
        tipText.setText(String.format("Tip: $%.2f", tipAmount));
        tipText.setTextSize(16);
        tipText.setTextColor(getResources().getColor(R.color.primary_background));
        tipText.setPadding(0, 10, 0, 20);

        // Add views to layout
        popupLayout.addView(titleText);
        popupLayout.addView(foodsText);
        popupLayout.addView(totalText);
        popupLayout.addView(addressText);
        popupLayout.addView(tipText);

        builder.setView(popupLayout);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        Log.d("CheckoutActivity", "Popup shown successfully");

        // Automatically close the popup and return to homepage after 10 seconds
        new Handler().postDelayed(() -> {
            dialog.dismiss();
            Log.d("CheckoutActivity", "Navigating to homepage");
            navigateToHomepage();
        }, 10000); // 10 seconds
    }

    private void navigateToHomepage() {
        Log.d("CheckoutActivity", "Returning to Homepage");
        Intent intent = new Intent(CheckoutActivity.this, HomepageFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
