package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private List<Restaurant> restaurantList;
    private List<Restaurant> filteredList;
    private RestaurantAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_fragment, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize data
        initRestaurantList();
        filteredList = new ArrayList<>(restaurantList);

        // Set up adapter
        adapter = new RestaurantAdapter(filteredList, restaurant -> {
            // Navigate to MenuFragment
            MenuFragment menuFragment = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("restaurant", restaurant);
            menuFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, menuFragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);

        // Set up search functionality
        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterResults(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterResults(newText);
                return false;
            }
        });

        return view;
    }

    private void initRestaurantList() {
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("Pasta Palace", "Authentic Italian pasta.", 4.5,
                new Address("123", "Main St", "10001"), 20.0));
        restaurantList.add(new Restaurant("Sushi Spot", "Fresh sushi and sashimi.", 4.8,
                new Address("456", "Elm St", "B2", "10002"), 30.0));
        restaurantList.add(new Restaurant("Burger Barn", "Gourmet burgers and fries.", 4.2,
                new Address("789", "Oak St", "10003"), 15.0));
        restaurantList.add(new Restaurant("Taco Town", "Mexican street food.", 4.6,
                new Address("321", "Maple Ave", "10004"), 10.0));
        restaurantList.add(new Restaurant("Pizza Plaza", "Wood-fired pizzas.", 4.7,
                new Address("654", "Pine Ave", "A1", "10005"), 18.0));
    }

    private void filterResults(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(restaurantList);
        } else {
            for (Restaurant restaurant : restaurantList) {
                if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(restaurant);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_search_fragment, container, false);
//
//        // Initialize the restaurant list with meal options
//        restaurantList = new ArrayList<>();
//        restaurantList.add(new Restaurant("Pasta Palace", "Authentic Italian pasta.", 4.5,
//                new Address("123", "Main St", "10001"),20.0));
//        restaurantList.add(new Restaurant("Sushi Spot", "Fresh sushi and sashimi.", 4.8,
//                new Address("456", "Elm St", "B2", "10002"),30.0));
//        restaurantList.add(new Restaurant("Burger Barn", "Gourmet burgers and fries.", 4.2,
//                new Address("789", "Oak St", "10003"),15.0));
//        restaurantList.add(new Restaurant("Taco Town", "Mexican street food.", 4.6,
//                new Address("321", "Maple Ave", "10004"),10.0));
//        restaurantList.add(new Restaurant("Pizza Plaza", "Wood-fired pizzas.", 4.7,
//                new Address("654", "Pine Ave", "A1", "10005"),18.0));
//        restaurantList.add(new Restaurant("Curry Corner", "Traditional Indian curries.", 4.3,
//                new Address("987", "Cedar Rd", "10006"),25.0));
//        restaurantList.add(new Restaurant("BBQ Haven", "Smoked meats and BBQ.", 4.9,
//                new Address("159", "Birch Rd", "10007"),35.0));
//        restaurantList.add(new Restaurant("Vegan Vibes", "Plant-based goodness.", 4.4,
//                new Address("753", "Spruce Rd", "C3", "10008"),22.0));
//        restaurantList.add(new Restaurant("Seafood Shack", "Fresh seafood daily.", 4.5,
//                new Address("951", "Willow Ln", "10009"),28.0));
//        restaurantList.add(new Restaurant("Bakery Bliss", "Breads and pastries.", 4.6,
//                new Address("852", "Fir Ln", "D4", "10010"),12.0));
//
//        filteredRestaurantList = new ArrayList<>(restaurantList);
//
//        ListView listView = view.findViewById(R.id.search_results_list_view);
//        adapter = new RestaurantListAdapter(getContext(), filteredRestaurantList);
//        listView.setAdapter(adapter);
//
//        SearchView searchView = view.findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filterRestaurants(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterRestaurants(newText);
//                return false;
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Restaurant selectedRestaurant = filteredRestaurantList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("restaurant", selectedRestaurant);
//
//                RestaurantDetailsFragment detailsFragment = new RestaurantDetailsFragment();
//                detailsFragment.setArguments(bundle);
//
//                FragmentManager fragmentManager = getParentFragmentManager(); // Use getParentFragmentManager() instead of getFragmentManager()
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_layout, detailsFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//
//        return view;
//    }
//
//    private void filterRestaurants(String query) {
//        filteredRestaurantList.clear();
//        if (query.isEmpty()) {
//            filteredRestaurantList.addAll(restaurantList);
//        } else {
//            for (Restaurant restaurant : restaurantList) {
//                if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
//                    filteredRestaurantList.add(restaurant);
//                }
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }
//}