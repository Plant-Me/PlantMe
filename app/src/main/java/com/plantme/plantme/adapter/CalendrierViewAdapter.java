package com.plantme.plantme.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.viewHolder.CalendrierViewHolder;
import com.plantme.plantme.R;
import com.plantme.plantme.model.Calendrier;

import java.util.List;

public class CalendrierViewAdapter extends RecyclerView.Adapter<CalendrierViewHolder> {
    private final List<Calendrier> calendrierList;

    public CalendrierViewAdapter(List<Calendrier> calendrierList) {
        this.calendrierList = calendrierList;
    }

    @NonNull
    @Override
    public CalendrierViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_calendrier_plante, viewGroup, false);
        return new CalendrierViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CalendrierViewHolder calendrierViewHolder, int i) {

        Calendrier calendrier = calendrierList.get(i);
        calendrierViewHolder.bind(calendrier);
    }

    @Override
    public int getItemCount() {
        return calendrierList.size();
    }

    public List<Calendrier> getCalendrierList() {
        return calendrierList;
    }

}
