package com.example.deliveryappYijia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliveryapp_finalproject_androidmobiledev.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    TextView tvCartItems;
    Button btnPlaceOrder;
    DatabaseReference database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvCartItems = findViewById(R.id.tv_cartItems);
        btnPlaceOrder = findViewById(R.id.btn_placeOrder);

        List<String> cartList = getIntent().getStringArrayListExtra("cart_list");

        if (cartList != null) {
            StringBuilder cartItemsText = new StringBuilder();
            for (String item : cartList) {
                cartItemsText.append(item).append("\n");
            }
            tvCartItems.setText(cartItemsText.toString());
        } else {
            tvCartItems.setText("No items in the cart.");
        }

        database = FirebaseDatabase.getInstance().getReference("orders");

        database.push().setValue("test");

        btnPlaceOrder.setOnClickListener(v -> saveOrder());
    }

    private void saveOrder() {
        String cartItemsText = tvCartItems.getText().toString().trim();
        if (!cartItemsText.isEmpty() && !cartItemsText.equals("No items in the cart.")) {
            
            String id = database.push().getKey();
            if (id == null) {
                Toast.makeText(CartActivity.this, "Failed to generate unique ID.", Toast.LENGTH_SHORT).show();
                return;
            }
            Order newOrder = new Order(id, cartItemsText);


            database.child(id).setValue(newOrder)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(CartActivity.this, "Order saved successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(CartActivity.this, "Failed to save order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(CartActivity.this, "No items in the cart.", Toast.LENGTH_SHORT).show();
        }
    }
}
