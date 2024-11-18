package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class DriverHomeActivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView greetingText, offerText, deliveryTimeText;
    private Button acceptButton, declineButton;
    private GoogleMap mMap;

    private final Random random = new Random();
    private final Handler handler = new Handler();
    private String currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        // Initialize views
        greetingText = findViewById(R.id.greetingText);
        offerText = findViewById(R.id.offerText);
        deliveryTimeText = findViewById(R.id.deliveryTimeText);
        acceptButton = findViewById(R.id.acceptButton);
        declineButton = findViewById(R.id.declineButton);

        // Setup Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        // Load initial offer
        loadNewOffer();

        // Set up reload every 15 seconds
        handler.postDelayed(this::loadNewOffer, 15000);

        // Accept or Decline button listeners
        acceptButton.setOnClickListener(v -> {
            Toast.makeText(this, "Offer Accepted!", Toast.LENGTH_SHORT).show();
            loadNewOffer();
        });

        declineButton.setOnClickListener(v -> {
            Toast.makeText(this, "Offer Declined!", Toast.LENGTH_SHORT).show();
            loadNewOffer();
        });
    }

    private void loadNewOffer() {
        FirebaseDatabase.getInstance().getReference("restaurants")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int index = random.nextInt((int) snapshot.getChildrenCount());
                        int i = 0;
                        for (DataSnapshot restaurant : snapshot.getChildren()) {
                            if (i == index) {
                                currentRestaurant = restaurant.child("name").getValue(String.class);
                                String deliveryTime = (5 + random.nextInt(26)) + " mins";

                                offerText.setText("New delivery request from: " + currentRestaurant);
                                deliveryTimeText.setText("Estimated Delivery Time: " + deliveryTime);
                                break;
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DriverHomeActivity.this, "Failed to load offer.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng currentLocation = new LatLng(-34, 151); // Replace with dynamic location
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
    }
}