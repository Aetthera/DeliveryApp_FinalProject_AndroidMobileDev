package com.example.deliveryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp_finalproject_androidmobiledev.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private RecyclerView choiceRecyclerView;
    private Button viewCartButton;

    private RestaurantAdapter restaurantAdapter;
    private CartAdapter cartAdapter;

    private List<String> restaurantList;
    private List<String> cartList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.edit_searchBar);
        choiceRecyclerView = findViewById(R.id.recycle_choiceRecyclerView);
        viewCartButton = findViewById(R.id.btn_viewCart);

        restaurantList = new ArrayList<>();
        cartList = new ArrayList<>();

        restaurantList.add("AA");
        restaurantList.add("BB");
        restaurantList.add("CC");

        restaurantAdapter = new RestaurantAdapter(this, restaurantList, this::openDetailActivity);
        cartAdapter = new CartAdapter(this, cartList);

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

        viewCartButton.setOnClickListener(v -> choiceRecyclerView.setAdapter(cartAdapter));
    }

    private void filterRestaurants(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String restaurant : restaurantList) {
            if (restaurant.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }
        restaurantAdapter = new RestaurantAdapter(this, filteredList, this::openDetailActivity);
        choiceRecyclerView.setAdapter(restaurantAdapter);
    }

    private void openDetailActivity(String restaurantName) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restaurant_name", restaurantName);
        startActivity(intent);
    }
}
