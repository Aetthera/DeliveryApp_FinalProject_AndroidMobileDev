package com.example.deliveryapp_finalproject_androidmobiledev;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
public class TipsActivity extends AppCompatActivity {

    private RecyclerView tipsRecyclerView;
    private TipsAdapter tipsAdapter;
    private List<Tip> tipsList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Setup RecyclerView
        tipsRecyclerView = findViewById(R.id.tipsRecyclerView);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch tips from Firestore
        fetchTipsFromFirestore();
    }

    private void fetchTipsFromFirestore() {
        CollectionReference tipsRef = db.collection("tips");

        tipsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                tipsList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Tip tip = document.toObject(Tip.class);
                    tipsList.add(tip);
                }
                tipsAdapter = new TipsAdapter(tipsList);
                tipsRecyclerView.setAdapter(tipsAdapter);
            } else {
                Toast.makeText(TipsActivity.this, "Failed to load tips", Toast.LENGTH_SHORT).show();
                Log.e("TipsActivity", "Error fetching tips", task.getException());
            }
        });
    }
}
