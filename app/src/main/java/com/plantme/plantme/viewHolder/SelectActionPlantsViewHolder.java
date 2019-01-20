package com.plantme.plantme.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.R;
import com.plantme.plantme.model.UserPlant;

public class SelectActionPlantsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvPlantNickName;
    private TextView tvPlantFrName;
    private ImageView ivPlant;


    public SelectActionPlantsViewHolder(View viewItem) {
        super(viewItem);

        tvPlantNickName = viewItem.findViewById(R.id.tvPlantNickName);
        tvPlantFrName = viewItem.findViewById(R.id.tvPlantFrName);
    }

    public void bind(UserPlant userPlant) {
        tvPlantNickName.setText(userPlant.getPlantName());
        tvPlantFrName.setText(userPlant.getPlant().getFrName());
        //ivPlant.setText(userPlant.getUserPlant().getImageUrl());
    }
}
