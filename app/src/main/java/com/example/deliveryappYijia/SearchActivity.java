package com.example.deliveryappYijia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp_finalproject_androidmobiledev.R;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;


public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private RecyclerView choiceRecyclerView;

    private RestaurantAdapter restaurantAdapter;

    private List<String> restaurantList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.edit_searchBar);
        choiceRecyclerView = findViewById(R.id.recycle_choiceRecyclerView);

        restaurantList = new ArrayList<>();


        restaurantList.add("AA");
        restaurantList.add("BB");
        restaurantList.add("CC");

        restaurantAdapter = new RestaurantAdapter(this, restaurantList, this::openDetailActivity);

        choiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        choiceRecyclerView.setAdapter(restaurantAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterRestaurants(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void filterRestaurants(String query) {
        Log.d("SearchActivity", "Filtering restaurants with query: " + query);
        List<String> filteredList = new ArrayList<>();
        for (String restaurant : restaurantList) {
            if (restaurant.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }
        Log.d("SearchActivity", "Filtered list: " + filteredList);
        restaurantAdapter = new RestaurantAdapter(this, filteredList, this::openDetailActivity);
        choiceRecyclerView.setAdapter(restaurantAdapter);
    }

    private void openDetailActivity(String restaurantName) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restaurant_name", restaurantName);
        startActivity(intent);
    }
}
