package com.plantme.plantme;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.model.Plant;

import java.util.List;

public class PlantViewAdapter extends RecyclerView.Adapter<MyPlantsViewHolder> {

    List<Plant> plantList;

    public PlantViewAdapter(List<Plant> plantList) {
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public MyPlantsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_my_plants,viewGroup,false);
        return new MyPlantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPlantsViewHolder myPlantsViewHolder, int i) {
        Plant plant = plantList.get(i);
        myPlantsViewHolder.bind(plant);
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }
}
