package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class DeliveryOrderAdapter extends ArrayAdapter<DeliveryOrder> {

    public DeliveryOrderAdapter(Context context, ArrayList<DeliveryOrder> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeliveryOrder order = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_delivery_order, parent, false);
        }

        TextView foodsText = convertView.findViewById(R.id.foods_text);
        TextView addressText = convertView.findViewById(R.id.address_text);
        TextView totalText = convertView.findViewById(R.id.total_text);

        if (order != null) {
            foodsText.setText("Foods: " + String.join(", ", order.getFoods()));
            addressText.setText("Address: " + order.getDeliveryAddress());
            totalText.setText(String.format("Total: $%.2f", order.getTotalAmount()));
        }

        return convertView;
    }
}
