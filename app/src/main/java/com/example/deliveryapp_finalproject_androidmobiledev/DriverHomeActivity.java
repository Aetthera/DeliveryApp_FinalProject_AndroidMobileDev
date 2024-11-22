package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DriverHomeActivity extends FragmentActivity {

    private FirebaseFirestore db;
    private TextView offerText, deliveryTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        db = FirebaseFirestore.getInstance();

        offerText = findViewById(R.id.offerText);
        deliveryTimeText = findViewById(R.id.deliveryTimeText);

        loadNewOffer();
    }

    private void loadNewOffer() {
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<QueryDocumentSnapshot> restaurantList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            restaurantList.add(document);
                        }

                        if (!restaurantList.isEmpty()) {
                            int randomIndex = new Random().nextInt(restaurantList.size());
                            QueryDocumentSnapshot selectedRestaurant = restaurantList.get(randomIndex);

                            String restaurantName = selectedRestaurant.getString("name");
                            String deliveryTime = (5 * (new Random().nextInt(6) + 1)) + " mins";

                            offerText.setText("New delivery request from: " + restaurantName);
                            deliveryTimeText.setText("Estimated Delivery Time: " + deliveryTime);
                        } else {
                            Toast.makeText(this, "No restaurants found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to load offers.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}