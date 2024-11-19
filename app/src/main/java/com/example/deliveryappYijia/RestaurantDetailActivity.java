package com.example.deliveryappYijia;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliveryapp_finalproject_androidmobiledev.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    TextView tvRestaurantName;
     TextView tvFoodName1;
    TextView tvFoodName2;
     TextView tvFoodName3;
     Button btnSelectFood1;
     Button btnSelectFood2;
     Button btnSelectFood3;
     Button btnViewCart;

     DatabaseReference database;

     List<String> cartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        tvRestaurantName = findViewById(R.id.tv_restaurantName);
        tvFoodName1 = findViewById(R.id.tv_foodName1);
        tvFoodName2 = findViewById(R.id.tv_foodName2);
        tvFoodName3 = findViewById(R.id.tv_foodName3);
        btnSelectFood1 = findViewById(R.id.btn_selectFood1);
        btnSelectFood2 = findViewById(R.id.btn_selectFood2);
        btnSelectFood3 = findViewById(R.id.btn_selectFood3);
        btnViewCart = findViewById(R.id.btn_viewCart);

        database = FirebaseDatabase.getInstance().getReference("test");
        cartList = new ArrayList<>();

        String restaurantName = getIntent().getStringExtra("restaurant_name");
        String foodName1 = "Food Item 1";
        String foodName2 = "Food Item 2";
        String foodName3 = "Food Item 3";

        tvRestaurantName.setText(restaurantName);
        tvFoodName1.setText(foodName1);
        tvFoodName2.setText(foodName2);
        tvFoodName3.setText(foodName3);

        btnSelectFood1.setOnClickListener(v -> addToCart(foodName1));
        btnSelectFood2.setOnClickListener(v -> addToCart(foodName2));
        btnSelectFood3.setOnClickListener(v -> addToCart(foodName3));

        btnViewCart.setOnClickListener(v -> openCartActivity());
    }

    private void addToCart(String foodName) {
        cartList.add(foodName);
        Toast.makeText(this, foodName + " added to cart", Toast.LENGTH_SHORT).show();
    }

    private void openCartActivity() {
        database.push().setValue("whateverrrr");
        Intent intent = new Intent(this, CartActivity.class);
        intent.putStringArrayListExtra("cart_list", new ArrayList<>(cartList));
        startActivity(intent);
    }
}
