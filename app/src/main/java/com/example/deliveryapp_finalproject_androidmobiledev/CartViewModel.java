package com.example.deliveryapp_finalproject_androidmobiledev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<String>> cartItems = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<String>> getCartItems() {
        return cartItems;
    }

    public void addToCart(String meal) {
        ArrayList<String> currentCart = cartItems.getValue();
        if (currentCart != null) {
            currentCart.add(meal);
            cartItems.setValue(currentCart);  // Update LiveData
        }
    }
}
