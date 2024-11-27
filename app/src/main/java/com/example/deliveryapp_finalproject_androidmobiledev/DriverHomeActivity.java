package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DriverHomeActivity extends FragmentActivity implements OnMapReadyCallback {

    private FirebaseFirestore db;
    private TextView offerText, deliveryTimeText;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        db = FirebaseFirestore.getInstance();

        offerText = findViewById(R.id.offerText);
        deliveryTimeText = findViewById(R.id.deliveryTimeText);

        // Load the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Error loading map", Toast.LENGTH_SHORT).show();
        }

        loadNewOffer();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    // Already on this page
                    return true;
                case R.id.nav_pending:
                    // Navigate to the PendingActivity
                    startActivity(new Intent(this, PendingActivity.class));
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        Toast.makeText(this, "Map is ready!", Toast.LENGTH_SHORT).show();

        // Example: Set up the map with default settings
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);
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
