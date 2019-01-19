package com.plantme.plantme.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;

public class ActionCalendarViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivActionImage;
    private TextView tvMyPlantName;
    private TextView tvActionName;

    public ActionCalendarViewHolder(View viewItem) {
        super(viewItem);

        ivActionImage = viewItem.findViewById(R.id.ivActionImage);
        tvMyPlantName = viewItem.findViewById(R.id.tvMyPlantName);
        tvActionName = viewItem.findViewById(R.id.tvActionName);
    }

    public void bind(CoupleActionDate coupleActionDate) {
        //ivActionImage.setactionPlant.getActionImage());
        tvMyPlantName.setText(coupleActionDate.getPlantName());
        tvActionName.setText(coupleActionDate.getUserAction().getActionName());
    }
}
