package com.example.tipsfinal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TipsFinal extends AppCompatActivity {

    // Firebase Database Reference
    private DatabaseReference databaseReference;

    // UI Components
    private TextView totalAmountTextView;
    private EditText customTipEditText;
    private RadioGroup tipOptionsGroup;
    private Button confirmTipButton;

    // Base fare for the trip
    private final double baseFare = 100.0; // Example amount (can be fetched dynamically)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_final);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryApp/Tips");

        // Bind UI components
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        customTipEditText = findViewById(R.id.customTipEditText);
        tipOptionsGroup = findViewById(R.id.tipOptionsGroup);
        confirmTipButton = findViewById(R.id.confirmTipButton);

        // Set base total amount initially
        updateTotalAmount(0);

        // Handle predefined tip percentage selection
        tipOptionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            double tipPercentage = 0;

            if (checkedId == R.id.tip15) {
                tipPercentage = 0.15;
            } else if (checkedId == R.id.tip20) {
                tipPercentage = 0.20;
            } else if (checkedId == R.id.tip35) {
                tipPercentage = 0.35;
            } else if (checkedId == R.id.tip50) {
                tipPercentage = 0.50;
            }

            updateTotalAmount(tipPercentage * baseFare);
        });

        // Handle custom tip input
        customTipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double customTip = Double.parseDouble(s.toString());
                    updateTotalAmount(customTip);
                } catch (NumberFormatException e) {
                    updateTotalAmount(0);
                }
            }
        });

        // Handle tip confirmation
        confirmTipButton.setOnClickListener(v -> {
            String customTip = customTipEditText.getText().toString();
            double tipAmount = customTip.isEmpty() ? 0 : Double.parseDouble(customTip);

            // Create Tip object
            String driverId = "driver123"; // Example driver ID (fetch dynamically in a real app)
            String timestamp = String.valueOf(System.currentTimeMillis());
            Tip tip = new Tip(driverId, baseFare, tipAmount, baseFare + tipAmount, timestamp);

            // Save to Firebase
            databaseReference.child(driverId).child(timestamp).setValue(tip)
                    .addOnSuccessListener(aVoid -> {
                        // Show Popup on Successful Tip Save
                        showSuccessPopup();
                    })
                    .addOnFailureListener(e -> Toast.makeText(TipsFinal.this, "Failed to save tip: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    // Helper method to update the total amount
    private void updateTotalAmount(double tipAmount) {
        double totalAmount = baseFare + tipAmount;
        totalAmountTextView.setText(String.format("Total with Tip: $%.2f", totalAmount));
    }

    // Helper method to display a success popup
    private void showSuccessPopup() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success");
        builder.setMessage("Tip saved successfully!");

        // Add a positive button (OK)
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Tip model class
    public static class Tip {
        public String driverId;
        public double baseFare;
        public double tipAmount;
        public double totalAmount;
        public String timestamp;

        public Tip() {} // Default constructor required for Firebase

        public Tip(String driverId, double baseFare, double tipAmount, double totalAmount, String timestamp) {
            this.driverId = driverId;
            this.baseFare = baseFare;
            this.tipAmount = tipAmount;
            this.totalAmount = totalAmount;
            this.timestamp = timestamp;
        }
    }
}
