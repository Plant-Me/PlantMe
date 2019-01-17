package com.plantme.plantme.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.viewHolder.ActionCalendarViewHolder;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.R;

import java.util.List;

public class ActionCalendarViewAdapter extends RecyclerView.Adapter<ActionCalendarViewHolder> {
    private final List<CoupleActionDate> listCoupleActionDates;

    public ActionCalendarViewAdapter(List<CoupleActionDate> listCoupleActionDates) {
        this.listCoupleActionDates = listCoupleActionDates;
    }

    @NonNull
    @Override
    public ActionCalendarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_calendar_actions, viewGroup, false);
        return new ActionCalendarViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ActionCalendarViewHolder actionWeekViewHolder, int i) {
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
