package com.plantme.plantme.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.viewHolder.ActionWeekViewHolder;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.R;

import java.util.List;

public class ActionWeekViewAdapter extends RecyclerView.Adapter<ActionWeekViewHolder> {
    private final List<CoupleActionDate> listCoupleActionDates;

    public ActionWeekViewAdapter(List<CoupleActionDate> listCoupleActionDates) {
        this.listCoupleActionDates = listCoupleActionDates;
    }

    @NonNull
    @Override
    public ActionWeekViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_week_actions, viewGroup, false);
        return new ActionWeekViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ActionWeekViewHolder actionWeekViewHolder, int i) {
        CoupleActionDate coupleActionDate = listCoupleActionDates.get(i);
        actionWeekViewHolder.bind(coupleActionDate);
    }

    @Override
    public int getItemCount() {
        return listCoupleActionDates.size();
    }

    public List<CoupleActionDate> getListCoupleActionDates() {
        return listCoupleActionDates;
    }

}
