package com.example.deliveryappYijia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp_finalproject_androidmobiledev.R;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<String> restaurantList;
    private OnItemClickListener onItemClickListener;

    public RestaurantAdapter(Context context, List<String> restaurantList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_restaurant_detail, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        String restaurantName = restaurantList.get(position);
        holder.restaurantNameTextView.setText(restaurantName);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(restaurantName));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String restaurantName);
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameTextView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.tv_restaurantName);
        }
    }
}

