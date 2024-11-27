package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        // Get the tab buttons
        Button homepageTab = findViewById(R.id.tab_homepage);
        Button searchTab = findViewById(R.id.tab_search);
        Button ordersTab = findViewById(R.id.tab_orders);
        Button rewardsTab = findViewById(R.id.tab_rewards);

        // Set event listeners
        homepageTab.setOnClickListener(v -> loadFragment(new HomepageFragment()));
        searchTab.setOnClickListener(v -> loadFragment(new SearchFragment()));
        ordersTab.setOnClickListener(v -> loadFragment(new OrdersFragment()));
        rewardsTab.setOnClickListener(v -> loadFragment(new RewardsFragment()));

        // Load the Homepage fragment by default
        loadFragment(new HomepageFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}