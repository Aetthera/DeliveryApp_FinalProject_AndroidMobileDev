package com.example.deliveryapp_finalproject_androidmobiledev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

    public RestaurantListAdapter(Context context, List<Restaurant> restaurantList) {
        super(context, 0, restaurantList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_restaurant, parent, false);
        }

        Restaurant restaurant = getItem(position);

        TextView name = convertView.findViewById(R.id.restaurant_name);
        TextView description = convertView.findViewById(R.id.restaurant_description);
        TextView rating = convertView.findViewById(R.id.restaurant_rating);
        TextView address = convertView.findViewById(R.id.restaurant_address);

        name.setText(restaurant.getName());
        description.setText(restaurant.getDescription());
        rating.setText("Rating: " + restaurant.getRating());
        address.setText(formatAddress(restaurant.getAddress()));

        return convertView;
    }

    private String formatAddress(Address address) {
        StringBuilder sb = new StringBuilder();
        sb.append(address.getHouseNumber()).append(" ").append(address.getStreetName());
        if (address.getApartment() != null) {
            sb.append(", Apt ").append(address.getApartment());
        }
        sb.append(", ").append(address.getZipcode());
        return sb.toString();
    }
}