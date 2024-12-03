package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class RewardsListAdapter extends ArrayAdapter<Reward> {

    private final OnRewardClickListener clickListener;

    public RewardsListAdapter(Context context, List<Reward> rewardsList, OnRewardClickListener clickListener) {
        super(context, 0, rewardsList);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reward, parent, false);
        }

        Reward reward = getItem(position);

        TextView name = convertView.findViewById(R.id.reward_name);
        TextView description = convertView.findViewById(R.id.reward_description);
        TextView pointsRequired = convertView.findViewById(R.id.reward_points_required);
        Button redeemButton = convertView.findViewById(R.id.redeem_button);

        name.setText(reward.getName());
        description.setText(reward.getDescription());
        pointsRequired.setText(reward.getPointsRequired() + " Points");

        redeemButton.setOnClickListener(v -> clickListener.onRewardClick(reward));

        return convertView;
    }

    public interface OnRewardClickListener {
        void onRewardClick(Reward reward);
    }
}
