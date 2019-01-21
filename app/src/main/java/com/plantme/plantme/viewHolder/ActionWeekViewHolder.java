package com.plantme.plantme.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.GlideApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;

public class ActionWeekViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivActionImage;
    private TextView tvMyPlantName;
    private TextView tvActionName;
    private TextView tvActionDate;
    private View view;

    public ActionWeekViewHolder(View viewItem) {
        super(viewItem);

        ivActionImage = viewItem.findViewById(R.id.ivActionImage);
        tvMyPlantName = viewItem.findViewById(R.id.tvMyPlantName);
        tvActionName = viewItem.findViewById(R.id.tvActionName);
        tvActionDate = viewItem.findViewById(R.id.tvActionDate);
        this.view = viewItem;
    }

    public void bind(CoupleActionDate coupleActionDate) {
        //ivActionImage.setactionPlant.getActionImage());
        tvMyPlantName.setText(coupleActionDate.getPlantName());
        tvActionName.setText(coupleActionDate.getUserAction().getActionName());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date coupleDate = coupleActionDate.getDateActuelle();
        Date today = new Date();
        String dateToday = formatter.format(today.getTime());
        String coupleDateString = formatter.format(coupleDate.getTime());

        if (!coupleDateString.equals(dateToday)) {

            String date = format.format(coupleDate);

            tvActionDate.setText(date);
        } else {
            tvActionDate.setText("");

        }

        GlideApp.with(view).load(coupleActionDate.getUserAction().getImageRId()).into(ivActionImage);

    }

}
