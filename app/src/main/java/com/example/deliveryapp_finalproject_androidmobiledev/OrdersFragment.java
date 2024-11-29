package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

// Inside your OrdersFragment
public class OrdersFragment extends Fragment {

    private CartViewModel cartViewModel;
    private TextView cartTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_orders_fragment, container, false);

        // Access the CartViewModel
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        cartTextView = view.findViewById(R.id.cart_items_text_view);

        // Observe changes in cart and update UI
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            if (cartItems != null && !cartItems.isEmpty()) {
                StringBuilder cartContents = new StringBuilder("Items in your cart:\n");
                for (String item : cartItems) {
                    cartContents.append(item).append("\n");
                }
                cartTextView.setText(cartContents.toString());
            } else {
                cartTextView.setText("Your cart is empty.");
            }
        });

        return view;
    }
}
