package com.plantme.plantme.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.plantme.plantme.R;
import com.plantme.plantme.viewHolder.UserPlantsViewHolder;

import com.plantme.plantme.model.UserPlant;

import java.util.ArrayList;
import java.util.List;

public class UserPlantViewAdapter extends RecyclerView.Adapter<UserPlantsViewHolder> implements Filterable {

    private final List<UserPlant> userPlantList;
    private List<UserPlant> userPlantListFiltered;

    public UserPlantViewAdapter(List<UserPlant> userPlantList) {
        this.userPlantList = userPlantList;
        userPlantListFiltered = new ArrayList<>();
        userPlantListFiltered.addAll(userPlantList);
    }

    @NonNull
    @Override
    public UserPlantsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_my_plants,viewGroup,false);
        return new UserPlantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserPlantsViewHolder plantsViewHolder, int i) {
        UserPlant plant = userPlantListFiltered.get(i);
        plantsViewHolder.bind(plant);
    }

    @Override
    public int getItemCount() {
        return userPlantListFiltered.size();
    }

    public List<UserPlant> getUserPlantList() {
        return userPlantList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    userPlantListFiltered.clear();
                    userPlantListFiltered.addAll(userPlantList);
                } else {
                    List<UserPlant> filteredList = new ArrayList<>();
                    for (UserPlant row : userPlantList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPlantName().toLowerCase().contains(charString.toLowerCase()) || row.getPlant().getFrName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    userPlantListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userPlantListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userPlantListFiltered = (ArrayList<UserPlant>) filterResults.values;

                // refresh the list with filtered data
                UserPlantViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

}
