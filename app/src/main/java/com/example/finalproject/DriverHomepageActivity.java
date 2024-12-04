package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DriverHomepageActivity extends AppCompatActivity {

    private ListView ordersListView;
    private DeliveryOrderAdapter adapter;
    private ArrayList<DeliveryOrder> ordersList;
    private DatabaseReference transactionsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_homepage);

        ordersListView = findViewById(R.id.orders_list_view);
        ordersList = new ArrayList<>();
        adapter = new DeliveryOrderAdapter(this, ordersList);
        ordersListView.setAdapter(adapter);

        // Reference the "transactions" node in Firebase
        transactionsRef = FirebaseDatabase.getInstance().getReference("transactions");

        // Fetch data from Firebase
        transactionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ordersList.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    // Parse each order
                    DeliveryOrder order = orderSnapshot.getValue(DeliveryOrder.class);
                    if (order != null && "Pending".equals(order.getStatus())) {
                        order.setId(orderSnapshot.getKey()); // Store Firebase ID in the order object
                        ordersList.add(order);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DriverHomepageActivity.this, "Failed to load orders: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Handle order selection
        ordersListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            DeliveryOrder selectedOrder = ordersList.get(position);
            if (selectedOrder != null) {
                markOrderAsDelivered(selectedOrder);
            }
        });
    }

    private void markOrderAsDelivered(DeliveryOrder order) {
        if (order.getId() == null) {
            Toast.makeText(this, "Order ID is null. Cannot update status.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference orderRef = transactionsRef.child(order.getId());
        orderRef.child("status").setValue("Delivered")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Order marked as delivered!", Toast.LENGTH_SHORT).show();
                    ordersList.remove(order);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update order: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
