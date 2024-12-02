package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class CartListAdapter extends ArrayAdapter<Food> {

    private final OnRemoveClickListener removeClickListener;

    public CartListAdapter(Context context, List<Food> cartItems, OnRemoveClickListener removeClickListener) {
        super(context, 0, cartItems);
        this.removeClickListener = removeClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
        }

        Food food = getItem(position);

        TextView name = convertView.findViewById(R.id.cart_food_name);
        TextView price = convertView.findViewById(R.id.cart_food_price);
        Button removeButton = convertView.findViewById(R.id.remove_button);

        name.setText(food.getName());
        price.setText("$" + food.getPrice());

        removeButton.setOnClickListener(v -> removeClickListener.onRemoveClick(food));

        return convertView;
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(Food food);
    }
}
