package com.plantme.plantme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.plantme.plantme.model.Plant;

public class MyPlantsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvPlantName;
    private TextView tvPlantType;
    private TextView tvPlantUsage;

    public MyPlantsViewHolder(View viewItem) {
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