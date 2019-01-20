package com.plantme.plantme.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.plantme.plantme.viewHolder.PlantsViewHolder;
//import com.plantme.plantme.PlantsViewHolder;
import com.plantme.plantme.PlantsViewHolder;
import com.plantme.plantme.R;
import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;
//import com.plantme.plantme.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class PlantViewAdapter extends RecyclerView.Adapter<PlantsViewHolder> implements Filterable {

    private final List<ResultAllPlant> plantList;
    private List<ResultAllPlant> plantListFiltered;

    public PlantViewAdapter(List<ResultAllPlant> resultAllPlants) {
        this.plantList = resultAllPlants;
        plantListFiltered = new ArrayList<>();
        plantListFiltered.addAll(plantList);
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_plants,viewGroup,false);
        return new PlantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantsViewHolder plantsViewHolder, int i) {
        ResultAllPlant resultAllPlant = plantListFiltered.get(i);
        plantsViewHolder.bind(resultAllPlant);
    }

    @Override
    public int getItemCount() {
        return plantListFiltered.size();
    }

    public List<ResultAllPlant> getPlantList() {
        return plantListFiltered;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    plantListFiltered.clear();
                    plantListFiltered.addAll(plantList);
                } else {
                    List<ResultAllPlant> filteredList = new ArrayList<>();
                    for (ResultAllPlant row : plantList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNomFr().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    plantListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = plantListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                plantListFiltered = (ArrayList<ResultAllPlant>) filterResults.values;

                // refresh the list with filtered data
                PlantViewAdapter.this.notifyDataSetChanged();
            }
        };
    }
}
