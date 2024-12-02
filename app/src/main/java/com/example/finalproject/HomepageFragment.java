package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment {

    private List<Restaurant> restaurantList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        // Initialize the ListView
        ListView listView = view.findViewById(R.id.list_view);
        initRestaurantList();

        // Set up adapter with click listener
        RestaurantListAdapter adapter = new RestaurantListAdapter(requireContext(), restaurantList, restaurant -> {
            // Navigate to MenuFragment
            MenuFragment menuFragment = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("restaurant", restaurant); // Pass selected restaurant
            menuFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, menuFragment)
                    .addToBackStack(null)
                    .commit();
        });
        listView.setAdapter(adapter);

        return view;
    }

    /**
     * Initialize the restaurant list with predefined data.
     */
    private void initRestaurantList() {
        restaurantList = new ArrayList<>();

        // Italian Restaurant
        restaurantList.add(new Restaurant("Pasta Palace", "Authentic Italian pasta dishes.", 4.5,
                new Address("123", "Main St", "10001"), 20.0));

        // Japanese Restaurant
        restaurantList.add(new Restaurant("Sushi Spot", "Fresh sushi and sashimi.", 4.8,
                new Address("456", "Elm St", "B2", "10002"), 30.0));

        // American Restaurant
        restaurantList.add(new Restaurant("Burger Barn", "Gourmet burgers and fries.", 4.2,
                new Address("789", "Oak St", "10003"), 15.0));

        // Mexican Restaurant
        restaurantList.add(new Restaurant("Taco Town", "Mexican street food.", 4.6,
                new Address("321", "Maple Ave", "10004"), 10.0));

        // Pizza Restaurant
        restaurantList.add(new Restaurant("Pizza Plaza", "Wood-fired pizzas and Italian desserts.", 4.7,
                new Address("654", "Pine Ave", "A1", "10005"), 18.0));

        // BBQ Restaurant
        restaurantList.add(new Restaurant("BBQ Haven", "Smoked meats and BBQ delights.", 4.9,
                new Address("951", "Birch Rd", "C3", "10006"), 35.0));

        // Vegan Restaurant
        restaurantList.add(new Restaurant("Vegan Vibes", "Plant-based dishes and vegan desserts.", 4.3,
                new Address("753", "Spruce St", "10007"), 25.0));

        // Seafood Restaurant
        restaurantList.add(new Restaurant("Seafood Shack", "Fresh seafood specialties.", 4.4,
                new Address("852", "Willow Ln", "D4", "10008"), 28.0));
    }
}


//package com.example.finalproject;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomepageFragment extends Fragment {
//
//    private List<Restaurant> restaurantList;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
//
//        // Find the ListView in the layout
//        ListView listView = view.findViewById(R.id.list_view);
//
//        // Initialize the restaurant list
//        initRestaurantList();
//
//        // Set the adapter
//        RestaurantListAdapter adapter = new RestaurantListAdapter(requireContext(), restaurantList);
//        listView.setAdapter(adapter);
//
//        return view;
//    }
//
//    /**
//     * Initialize the restaurant list with predefined data.
//     */
//    private void initRestaurantList() {
//        restaurantList = new ArrayList<>();
//        restaurantList.add(new Restaurant("Pasta Palace", "Authentic Italian pasta.", 4.5,
//                new Address("123", "Main St", "10001"), 20.0));
//        restaurantList.add(new Restaurant("Sushi Spot", "Fresh sushi and sashimi.", 4.8,
//                new Address("456", "Elm St", "B2", "10002"), 30.0));
//        restaurantList.add(new Restaurant("Burger Barn", "Gourmet burgers and fries.", 4.2,
//                new Address("789", "Oak St", "10003"), 15.0));
//        restaurantList.add(new Restaurant("Taco Town", "Mexican street food.", 4.6,
//                new Address("321", "Maple Ave", "10004"), 10.0));
//        restaurantList.add(new Restaurant("Pizza Plaza", "Wood-fired pizzas.", 4.7,
//                new Address("654", "Pine Ave", "A1", "10005"), 18.0));
//        restaurantList.add(new Restaurant("Curry Corner", "Traditional Indian curries.", 4.3,
//                new Address("987", "Cedar Rd", "10006"), 25.0));
//        restaurantList.add(new Restaurant("BBQ Haven", "Smoked meats and BBQ.", 4.9,
//                new Address("159", "Birch Rd", "10007"), 35.0));
//        restaurantList.add(new Restaurant("Vegan Vibes", "Plant-based goodness.", 4.4,
//                new Address("753", "Spruce Rd", "C3", "10008"), 22.0));
//        restaurantList.add(new Restaurant("Seafood Shack", "Fresh seafood daily.", 4.5,
//                new Address("951", "Willow Ln", "10009"), 28.0));
//        restaurantList.add(new Restaurant("Bakery Bliss", "Breads and pastries.", 4.6,
//                new Address("852", "Fir Ln", "D4", "10010"), 12.0));
//    }
//}
