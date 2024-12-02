package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class RewardsFragment extends Fragment {

    private DatabaseReference database;
    private int userPoints; // User's total reward points
    private List<Reward> rewardsList;
    private RewardsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        TextView pointsTextView = view.findViewById(R.id.user_points_text);
        ListView rewardsListView = view.findViewById(R.id.rewards_list_view);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().getReference();

        // Load user points
        database.child("users").child("userId").child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer points = snapshot.getValue(Integer.class);
                if (points == null) {
                    // Default points value for users without points
                    points = 0;
                    database.child("users").child("userId").child("points").setValue(points);
                }
                userPoints = points;
                pointsTextView.setText("Your Points: " + userPoints);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load points", Toast.LENGTH_SHORT).show();
            }
        });


        // Load rewards
        rewardsList = new ArrayList<>();
        adapter = new RewardsListAdapter(requireContext(), rewardsList, reward -> {
            if (userPoints >= reward.getPointsRequired()) {
                redeemReward(reward);
            } else {
                Toast.makeText(getContext(), "Not enough points to redeem this reward", Toast.LENGTH_SHORT).show();
            }
        });
        rewardsListView.setAdapter(adapter);

        database.child("rewards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rewardsList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Reward reward = data.getValue(Reward.class);
                    if (reward != null) {
                        reward.setId(data.getKey());
                        rewardsList.add(reward);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load rewards", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void redeemReward(Reward reward) {
        // Deduct points from user
        database.child("users").child("userId").child("points")
                .setValue(userPoints - reward.getPointsRequired())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Reward redeemed: " + reward.getName(), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to redeem reward", Toast.LENGTH_SHORT).show();
                });
    }
}
