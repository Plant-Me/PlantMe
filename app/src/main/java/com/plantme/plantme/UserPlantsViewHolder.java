package com.plantme.plantme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.UserPlant;

public class UserPlantsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvPlantNickName;
    private TextView tvPlantFrName;
    private ImageView ivPlant;

    public UserPlantsViewHolder(View viewItem) {
        super(viewItem);

        tvPlantNickName = viewItem.findViewById(R.id.tvPlantNickName);
        tvPlantFrName = viewItem.findViewById(R.id.tvPlantFrName);
    }

    public void bind(UserPlant userPlant) {
        tvPlantNickName.setText(userPlant.getUserPlant().getImageUrl());
        tvPlantFrName.setText(userPlant.getPlantName());
        //ivPlant.setText(userPlant.getUserPlant().getImageUrl());
    }
}
