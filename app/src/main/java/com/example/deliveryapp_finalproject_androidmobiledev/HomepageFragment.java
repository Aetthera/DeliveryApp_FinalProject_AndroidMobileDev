package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomepageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homepage_fragment, container, false);

        ListView listView = view.findViewById(R.id.list_view);

        // List of restaurants
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("Pasta Palace", "Authentic Italian pasta.", 4.5,
                new Address("123", "Main St", "10001")));
        restaurantList.add(new Restaurant("Sushi Spot", "Fresh sushi and sashimi.", 4.8,
                new Address("456", "Elm St", "B2", "10002")));
        restaurantList.add(new Restaurant("Burger Barn", "Gourmet burgers and fries.", 4.2,
                new Address("789", "Oak St", "10003")));
        restaurantList.add(new Restaurant("Taco Town", "Mexican street food.", 4.6,
                new Address("321", "Maple Ave", "10004")));
        restaurantList.add(new Restaurant("Pizza Plaza", "Wood-fired pizzas.", 4.7,
                new Address("654", "Pine Ave", "A1", "10005")));
        restaurantList.add(new Restaurant("Curry Corner", "Traditional Indian curries.", 4.3,
                new Address("987", "Cedar Rd", "10006")));
        restaurantList.add(new Restaurant("BBQ Haven", "Smoked meats and BBQ.", 4.9,
                new Address("159", "Birch Rd", "10007")));
        restaurantList.add(new Restaurant("Vegan Vibes", "Plant-based goodness.", 4.4,
                new Address("753", "Spruce Rd", "C3", "10008")));
        restaurantList.add(new Restaurant("Seafood Shack", "Fresh seafood daily.", 4.5,
                new Address("951", "Willow Ln", "10009")));
        restaurantList.add(new Restaurant("Bakery Bliss", "Breads and pastries.", 4.6,
                new Address("852", "Fir Ln", "D4", "10010")));

        // Set the adapter
        RestaurantListAdapter adapter = new RestaurantListAdapter(getContext(), restaurantList);
        listView.setAdapter(adapter);

        return view;
    }
}