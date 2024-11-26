package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PendingActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private TextView restaurantNameText, restaurantAddressText, deliveryTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        firestore = FirebaseFirestore.getInstance();

        restaurantNameText = findViewById(R.id.restaurantNameText);
        restaurantAddressText = findViewById(R.id.restaurantAddressText);
        deliveryTimeText = findViewById(R.id.deliveryTimeText);

        loadCurrentDelivery();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, DriverHomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_pending) {
                // Already on PendingActivity
                return true;
            }
            return false;
        });
    }

    private void loadCurrentDelivery() {
        firestore.collection("deliveries")
                .document("currentDelivery")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String restaurantName = documentSnapshot.getString("restaurantName");
                        String restaurantAddress = documentSnapshot.getString("restaurantAddress");
                        String deliveryTime = documentSnapshot.getString("deliveryTime");

                        restaurantNameText.setText("Restaurant: " + restaurantName);
                        restaurantAddressText.setText("Address: " + restaurantAddress);
                        deliveryTimeText.setText("Delivery Time: " + deliveryTime);
                    } else {
                        Toast.makeText(this, "No active delivery found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load delivery details.", Toast.LENGTH_SHORT).show());
    }
}