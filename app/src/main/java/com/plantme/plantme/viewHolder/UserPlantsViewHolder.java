package com.plantme.plantme.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.plantme.plantme.R;
import com.plantme.plantme.model.GlideApp;
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
        ivPlant = viewItem.findViewById(R.id.ivPlant);
    }

    public void bind(UserPlant userPlant) {
        tvPlantNickName.setText(userPlant.getPlantName());
        tvPlantFrName.setText(userPlant.getPlant().getFrName());
        GlideApp.with(ivPlant).load(userPlant.getPlant().getImageUrl()).placeholder(R.drawable.ic_green_tea).into(ivPlant);
        //ivPlant.setText(userPlant.getPlant().getImageUrl());
    }
}
