package com.plantme.plantme.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.plantme.plantme.R;
import com.plantme.plantme.model.Plant;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

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
