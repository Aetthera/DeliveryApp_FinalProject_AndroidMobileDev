package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {

    private final OnFoodClickListener clickListener;

    public FoodListAdapter(Context context, List<Food> foodList, OnFoodClickListener clickListener) {
        super(context, 0, foodList);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_food, parent, false);
        }

        Food food = getItem(position);

        ImageView image = convertView.findViewById(R.id.food_image);
        TextView name = convertView.findViewById(R.id.food_name);
        TextView price = convertView.findViewById(R.id.food_price);
        Button addButton = convertView.findViewById(R.id.add_to_cart_button);

        name.setText(food.getName());
        price.setText("$" + food.getPrice());

        // Load the image
        int imageResId = getContext().getResources().getIdentifier(food.getImageUrl(), "drawable", getContext().getPackageName());
        image.setImageResource(imageResId);

        addButton.setOnClickListener(v -> {
            // Add to Firebase Realtime Database
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("cart");
            String cartItemId = database.push().getKey(); // Unique ID for each cart item
            if (cartItemId != null) {
                database.child(cartItemId).setValue(food)
                        .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to Add to Cart", Toast.LENGTH_SHORT).show());
            }
        });

        return convertView;
    }

    public interface OnFoodClickListener {
        void onFoodClick(Food food);
    }
}
