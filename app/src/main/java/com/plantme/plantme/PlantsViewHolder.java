package com.plantme.plantme;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.fragment.AllPlantsFragment;
import com.plantme.plantme.model.GlideApp;
import com.plantme.plantme.model.MyGlideAppModule;
import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;

//import com.plantme.plantme.model.Plant;

public class PlantsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvPlantName;
    private TextView tvPlantUsage;
    private ImageView ivPlant;
    private View v;


    public PlantsViewHolder(View viewItem) {
        super(viewItem);

        tvPlantName = viewItem.findViewById(R.id.tvPlantName);
        ivPlant = viewItem.findViewById(R.id.ivPlant);
        tvPlantUsage = viewItem.findViewById(R.id.tvPlantUsage);
        v = viewItem;

    }

    public void bind(ResultAllPlant resultAllPlant) {
        tvPlantName.setText(resultAllPlant.getNomFr());
        tvPlantUsage.setText(resultAllPlant.getUsageMilieu());
        GlideApp.with(v).load(resultAllPlant.getImage().getUrl()).placeholder(R.drawable.ic_green_tea).into(ivPlant);
        //Log.d("image url", "bind: " + resultAllPlant.getImage().getUrl());


    }


}
