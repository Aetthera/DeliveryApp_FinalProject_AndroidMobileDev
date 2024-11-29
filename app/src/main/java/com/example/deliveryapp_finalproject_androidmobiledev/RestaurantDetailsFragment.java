package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RestaurantDetailsFragment extends Fragment {

    private RestaurantDetail restaurantDetail;
    private CartViewModel cartViewModel;
    private DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        // Get the restaurant details from the arguments
        if (getArguments() != null) {
            restaurantDetail = (RestaurantDetail) getArguments().getSerializable("restaurantDetail");
        }

        // Initialize the views
        TextView nameTextView = view.findViewById(R.id.restaurant_name);
        TextView meal1TextView = view.findViewById(R.id.meal_1);
        TextView meal2TextView = view.findViewById(R.id.meal_2);
        TextView meal3TextView = view.findViewById(R.id.meal_3);
        Button meal1Button = view.findViewById(R.id.meal_1_button);
        Button meal2Button = view.findViewById(R.id.meal_2_button);
        Button meal3Button = view.findViewById(R.id.meal_3_button);
        Button viewCartButton = view.findViewById(R.id.view_cart_button);

        // Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("orders");

        if (restaurantDetail != null) {
            nameTextView.setText(restaurantDetail.getName());
            meal1TextView.setText(restaurantDetail.getMeal1());
            meal2TextView.setText(restaurantDetail.getMeal2());
            meal3TextView.setText(restaurantDetail.getMeal3());
        }

        // Add meals to cart
        meal1Button.setOnClickListener(v -> addToCart(meal1TextView.getText().toString()));
        meal2Button.setOnClickListener(v -> addToCart(meal2TextView.getText().toString()));
        meal3Button.setOnClickListener(v -> addToCart(meal3TextView.getText().toString()));

        // View cart button click
        viewCartButton.setOnClickListener(v -> viewCart());

        return view;
    }

    private void addToCart(String meal) {
        // Add meal to cart using ViewModel
        cartViewModel.addToCart(meal);
        Toast.makeText(getContext(), meal + " added to cart", Toast.LENGTH_SHORT).show();
    }

    // Inside your RestaurantDetailsFragment
    private void viewCart() {
        // Get cart items from the ViewModel
        ArrayList<String> cartItems = new ArrayList<>(cartViewModel.getCartItems().getValue());

        // Add the order to Firebase if not empty
        if (!cartItems.isEmpty()) {
            Order order = new Order(cartItems);
            String id = database.push().getKey();
            if (id != null) {
                database.child(id).setValue(order);
                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No items in cart", Toast.LENGTH_SHORT).show();
        }

        // Navigate to OrdersFragment without passing data through Bundle
        OrdersFragment ordersFragment = new OrdersFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, ordersFragment)
                .addToBackStack(null)
                .commit();
    }

}
