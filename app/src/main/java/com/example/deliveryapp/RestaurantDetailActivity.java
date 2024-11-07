package com.example.deliveryapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliveryapp_finalproject_androidmobiledev.R;

public class RestaurantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        String restaurantName = getIntent().getStringExtra("restaurant_name");

        TextView restaurantNameTextView = findViewById(R.id.tv_restaurantName);
        restaurantNameTextView.setText(restaurantName);
    }
}