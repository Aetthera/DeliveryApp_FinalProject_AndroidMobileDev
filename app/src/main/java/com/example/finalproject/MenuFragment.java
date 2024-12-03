package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private List<Food> foodList;
    private FoodListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Retrieve restaurant data from arguments
        if (getArguments() != null) {
            Restaurant restaurant = (Restaurant) getArguments().getSerializable("restaurant");
            if (restaurant != null) {
                Log.d("MenuFragment", "Selected Restaurant: " + restaurant.getName());
                initFoodList(restaurant); // Populate food list based on restaurant
            } else {
                Log.e("MenuFragment", "Restaurant is null");
            }
        } else {
            Log.e("MenuFragment", "Arguments are null");
        }

        // Set up ListView and adapter
        ListView listView = view.findViewById(R.id.food_list_view);
        adapter = new FoodListAdapter(requireContext(), foodList, food -> {
            Log.d("MenuFragment", "Adding food to cart: " + food.getName());
            // Handle adding food to cart (this can be extended to use Firebase or local storage)
        });
        listView.setAdapter(adapter);

        // Set up "View Cart" button
        Button viewCartButton = view.findViewById(R.id.view_cart_button);
        viewCartButton.setOnClickListener(v -> {
            CartFragment cartFragment = new CartFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, cartFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void initFoodList(Restaurant restaurant) {
        foodList = new ArrayList<>();

        switch (restaurant.getName()) {
            case "Pasta Palace":
                foodList.add(new Food("Spaghetti Bolognese", 12.99, "pasta_bolognese"));
                foodList.add(new Food("Penne Alfredo", 14.99, "penne_alfredo"));
                foodList.add(new Food("Garlic Bread", 4.99, "garlic_bread"));
                break;

            case "Sushi Spot":
                foodList.add(new Food("California Roll", 9.99, "california_roll"));
                foodList.add(new Food("Tuna Sashimi", 15.99, "tuna_sashimi"));
                foodList.add(new Food("Miso Soup", 3.99, "miso_soup"));
                break;

            case "Burger Barn":
                foodList.add(new Food("Cheeseburger", 8.99, "cheeseburger"));
                foodList.add(new Food("Bacon Burger", 10.99, "bacon_burger"));
                foodList.add(new Food("French Fries", 3.99, "french_fries"));
                break;

            case "Taco Town":
                foodList.add(new Food("Beef Tacos", 7.99, "beef_tacos"));
                foodList.add(new Food("Chicken Quesadilla", 9.99, "chicken_quesadilla"));
                foodList.add(new Food("Guacamole & Chips", 5.99, "guacamole_chips"));
                break;

            case "Pizza Plaza":
                foodList.add(new Food("Margherita Pizza", 10.99, "margherita_pizza"));
                foodList.add(new Food("Pepperoni Pizza", 12.99, "pepperoni_pizza"));
                foodList.add(new Food("Tiramisu", 6.99, "tiramisu"));
                break;

            case "BBQ Haven":
                foodList.add(new Food("Smoked Ribs", 18.99, "smoked_ribs"));
                foodList.add(new Food("Pulled Pork Sandwich", 14.99, "pulled_pork"));
                foodList.add(new Food("BBQ Chicken", 16.99, "bbq_chicken"));
                break;

            case "Vegan Vibes":
                foodList.add(new Food("Vegan Wrap", 8.99, "vegan_wrap"));
                foodList.add(new Food("Quinoa Salad", 7.99, "quinoa_salad"));
                foodList.add(new Food("Vegan Brownie", 5.99, "vegan_brownie"));
                break;

            case "Seafood Shack":
                foodList.add(new Food("Grilled Salmon", 17.99, "grilled_salmon"));
                foodList.add(new Food("Shrimp Scampi", 16.99, "shrimp_scampi"));
                foodList.add(new Food("Clam Chowder", 8.99, "clam_chowder"));
                break;

            default:
                Log.e("MenuFragment", "No menu found for restaurant: " + restaurant.getName());
                break;
        }
    }
}
