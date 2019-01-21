package com.plantme.plantme.viewHolder;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.plantme.plantme.R;
import com.plantme.plantme.model.Calendrier;


public class CalendrierViewHolder extends RecyclerView.ViewHolder {
    private TextView nomCalendrier;
    private TextView tableRowJanvier;
    private TextView tableRowFevrier;
    private TextView tableRowMars;
    private TextView tableRowAvril;
    private TextView tableRowMai;
    private TextView tableRowJuin;
    private TextView tableRowJuillet;
    private TextView tableRowAout;
    private TextView tableRowSeptembre;
    private TextView tableRowOctobre;
    private TextView tableRowNovembre;
    private TextView tableRowDecembre;


    public CalendrierViewHolder(View viewItem) {
        super(viewItem);

        nomCalendrier = viewItem.findViewById(R.id.rvNomCalendrier);
        tableRowJanvier = viewItem.findViewById(R.id.tableRowJanvier);
        tableRowFevrier = viewItem.findViewById(R.id.tableRowFevrier);
        tableRowMars = viewItem.findViewById(R.id.tableRowMars);
        tableRowAvril = viewItem.findViewById(R.id.tableRowAvril);
        tableRowMai = viewItem.findViewById(R.id.tableRowMai);
        tableRowJuin = viewItem.findViewById(R.id.tableRowJuin);
        tableRowJuillet = viewItem.findViewById(R.id.tableRowJuillet);
        tableRowAout = viewItem.findViewById(R.id.tableRowAout);
        tableRowSeptembre = viewItem.findViewById(R.id.tableRowSeptembre);
        tableRowOctobre = viewItem.findViewById(R.id.tableRowOctobre);
        tableRowNovembre = viewItem.findViewById(R.id.tableRowNovembre);
        tableRowDecembre = viewItem.findViewById(R.id.tableRowDecembre);

    }

    public void bind(Calendrier calendrier) {
        //ivActionImage.setactionPlant.getActionImage());
        nomCalendrier.setText(calendrier.getActionName());
        if (calendrier.isJanvier()) {
//            tableRowJanvier
        } else {
            tableRowJanvier.setPaintFlags(tableRowJanvier.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isFevrier()) {
//            tableRowFevrier
        } else {
            tableRowFevrier.setPaintFlags(tableRowFevrier.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isMars()) {
//            tableRowMars
        } else {
            tableRowMars.setPaintFlags(tableRowMars.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isAvril()) {
//            tableRowAvril
        } else {
            tableRowAvril.setPaintFlags(tableRowAvril.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isMai()) {
//            tableRowMai
        } else {
            tableRowMai.setPaintFlags(tableRowMai.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isJuin()) {
//            tableRowJuin
        } else {
            tableRowJuin.setPaintFlags(tableRowJuin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isJuillet()) {
//            tableRowJuillet
        } else {
            tableRowJuillet.setPaintFlags(tableRowJuillet.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isAout()) {
//            tableRowAout
        } else {
            tableRowAout.setPaintFlags(tableRowAout.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isSeptembre()) {
//            tableRowSeptembre
        } else {
            tableRowSeptembre.setPaintFlags(tableRowSeptembre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isOctobre()) {
//            tableRowOctobre
        } else {
            tableRowOctobre.setPaintFlags(tableRowOctobre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isNovembre()) {
//            tableRowNovembre
        } else {
            tableRowNovembre.setPaintFlags(tableRowNovembre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (calendrier.isDecembre()) {
//            tableRowDecembre
        } else {
            tableRowDecembre.setPaintFlags(tableRowDecembre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

}