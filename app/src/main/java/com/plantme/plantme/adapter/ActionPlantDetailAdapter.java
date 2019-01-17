package com.plantme.plantme.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.ActionPlantDetailViewHolder;
import com.plantme.plantme.ActionPlantsViewHolder;
import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;

import java.util.List;

public class ActionPlantDetailAdapter extends RecyclerView.Adapter<ActionPlantDetailViewHolder>{
    private final List<CoupleActionDate> listCoupleActionDates;

    public ActionPlantDetailAdapter(List<CoupleActionDate> listCoupleActionDates) {
        this.listCoupleActionDates = listCoupleActionDates;
    }

    @NonNull
    @Override
    public ActionPlantDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_action_plantes_details, viewGroup, false);
        return new ActionPlantDetailViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ActionPlantDetailViewHolder actionPlantDetailViewHolder, int i) {

        CoupleActionDate coupleActionDate = listCoupleActionDates.get(i);
        actionPlantDetailViewHolder.bind(coupleActionDate);
    }

    @Override
    public int getItemCount() {
        return listCoupleActionDates.size();
    }

    public List<CoupleActionDate> getListCoupleActionDates() {
        return listCoupleActionDates;
    }

}