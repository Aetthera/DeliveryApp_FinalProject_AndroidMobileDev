package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private DatabaseReference database;
    private List<Food> cartItems;
    private CartListAdapter adapter;
    private TextView totalAmountTextView;
    private double totalAmount = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("cart");

        cartItems = new ArrayList<>();
        ListView listView = view.findViewById(R.id.cart_list_view);
        totalAmountTextView = view.findViewById(R.id.total_amount_text);
        Button checkoutButton = view.findViewById(R.id.checkout_button);

        // Set up adapter
        adapter = new CartListAdapter(requireContext(), cartItems, food -> {
            // Remove food from Firebase and update the UI
            database.child(food.getId()).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), food.getName() + " removed from cart", Toast.LENGTH_SHORT).show();
                        updateTotalAmount();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show());
        });
        listView.setAdapter(adapter);

        // Retrieve cart items from Firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                totalAmount = 0.0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Food food = data.getValue(Food.class);
                    if (food != null) {
                        food.setId(data.getKey()); // Save Firebase ID for removal
                        cartItems.add(food);
                        totalAmount += food.getPrice(); // Calculate total amount
                    }
                }
                adapter.notifyDataSetChanged();
                updateTotalAmount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show();
            }
        });

        // Checkout button listener
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext(), CheckoutActivity.class);
                intent.putExtra("cart_total", totalAmount); // Pass total amount to CheckoutActivity
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateTotalAmount() {
        totalAmountTextView.setText(String.format("Total: $%.2f", totalAmount));
    }
}

//package com.example.finalproject;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartFragment extends Fragment {
//
//    private DatabaseReference database;
//    private List<Food> cartItems;
//    private CartListAdapter adapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//
//        // Initialize Firebase Database reference
//        database = FirebaseDatabase.getInstance().getReference("cart");
//
//        cartItems = new ArrayList<>();
//        ListView listView = view.findViewById(R.id.cart_list_view);
//
//        // Set up adapter
//        adapter = new CartListAdapter(requireContext(), cartItems, food -> {
//            // Remove food from Firebase and update the UI
//            database.child(food.getId()).removeValue()
//                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), food.getName() + " removed from cart", Toast.LENGTH_SHORT).show())
//                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show());
//        });
//        listView.setAdapter(adapter);
//
//        // Retrieve cart items from Firebase
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                cartItems.clear();
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    Food food = data.getValue(Food.class);
//                    if (food != null) {
//                        food.setId(data.getKey()); // Save Firebase ID for removal
//                        cartItems.add(food);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return view;
//    }
//}
