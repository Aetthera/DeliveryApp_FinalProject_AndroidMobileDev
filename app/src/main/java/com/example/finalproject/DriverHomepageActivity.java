package com.example.finalproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class DriverHomepageActivity extends AppCompatActivity {

    private ListView ordersListView;
    private ArrayList<DeliveryOrder> ordersList;
    private DeliveryOrderAdapter adapter;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_homepage);

        ordersListView = findViewById(R.id.orders_list_view);
        ordersList = new ArrayList<>();
        adapter = new DeliveryOrderAdapter(this, ordersList);
        ordersListView.setAdapter(adapter);

        ordersRef = FirebaseDatabase.getInstance().getReference("delivery_orders");

        fetchOrders();

        ordersListView.setOnItemClickListener((parent, view, position, id) -> {
            DeliveryOrder selectedOrder = ordersList.get(position);
            assignOrderToDriver(selectedOrder);
        });
    }

    private void fetchOrders() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ordersList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    DeliveryOrder order = data.getValue(DeliveryOrder.class);
                    if (order != null && "Pending".equals(order.getStatus())) {
                        ordersList.add(order);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DriverHomepageActivity.this, "Failed to load orders", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void assignOrderToDriver(DeliveryOrder order) {
        String driverId = "driver123"; // Replace with actual driver ID

        order.setStatus("In Progress");
        ordersRef.child(order.getId()).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Order assigned successfully!", Toast.LENGTH_SHORT).show();
                    updateDriverOrder(driverId, order.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to assign order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateDriverOrder(String driverId, String orderId) {
        DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference("drivers").child(driverId);
        driverRef.child("currentOrderId").setValue(orderId);
    }
}
