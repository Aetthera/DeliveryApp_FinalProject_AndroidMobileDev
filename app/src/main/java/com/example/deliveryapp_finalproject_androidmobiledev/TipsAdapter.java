package com.example.deliveryapp_finalproject_androidmobiledev;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipViewHolder> {

    private List<Tip> tipsList;

    public TipsAdapter(List<Tip> tipsList) {
        this.tipsList = tipsList;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        Tip tip = tipsList.get(position);
        holder.tipTitle.setText(tip.getTitle());
        holder.tipContent.setText(tip.getContent());
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    public static class TipViewHolder extends RecyclerView.ViewHolder {
        TextView tipTitle, tipContent;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            tipTitle = itemView.findViewById(R.id.tipTitle);
            tipContent = itemView.findViewById(R.id.tipContent);
        }
    }
}