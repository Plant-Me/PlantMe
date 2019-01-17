package com.plantme.plantme.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.ActionPlantsViewHolder;

import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;

import java.util.List;

public class ActionViewAdapter extends RecyclerView.Adapter<ActionPlantsViewHolder> {
    private final List<CoupleActionDate> listCoupleActionDates;

    public ActionViewAdapter(List<CoupleActionDate> listCoupleActionDates) {
        this.listCoupleActionDates = listCoupleActionDates;
    }

    @NonNull
    @Override
    public ActionPlantsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_plant_actions_day, viewGroup, false);
        return new ActionPlantsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ActionPlantsViewHolder actionPlantsViewHolder, int i) {

        CoupleActionDate coupleActionDate = listCoupleActionDates.get(i);
        actionPlantsViewHolder.bind(coupleActionDate);
    }

    @Override
    public int getItemCount() {
        return listCoupleActionDates.size();
    }

    public List<CoupleActionDate> getListCoupleActionDates() {
        return listCoupleActionDates;
    }

}
