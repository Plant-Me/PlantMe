package com.plantme.plantme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.User;
import com.plantme.plantme.model.UserPlant;

public class PlantsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvPlantName;
    private TextView tvPlantType;
    private TextView tvPlantUsage;

    public PlantsViewHolder(View viewItem) {
        super(viewItem);

        tvPlantName = viewItem.findViewById(R.id.tvPlantName);
        tvPlantType = viewItem.findViewById(R.id.tvPlantType);
        tvPlantUsage = viewItem.findViewById(R.id.tvPlantUsage);
    }

    public void bind(Plant plant) {
        tvPlantName.setText(plant.getFrName());
        tvPlantType.setText(plant.getType());
        tvPlantUsage.setText(plant.getUsage());
    }
}
