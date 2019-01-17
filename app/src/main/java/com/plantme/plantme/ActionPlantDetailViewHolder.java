package com.plantme.plantme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.model.CoupleActionDate;

import java.text.SimpleDateFormat;

public class ActionPlantDetailViewHolder extends RecyclerView.ViewHolder{
    private ImageView ivActionImage;
    private TextView typeAction;
    private TextView dateAction;

    public ActionPlantDetailViewHolder(View viewItem) {
        super(viewItem);
        ivActionImage = viewItem.findViewById(R.id.rowActionImageView);
        typeAction = viewItem.findViewById(R.id.rowActionTypeAction);
        dateAction = viewItem.findViewById(R.id.rowActionDate);

    }

    public void bind(CoupleActionDate coupleActionDate) {
        //ivActionImage.setactionPlant.getActionImage());
        typeAction.setText(coupleActionDate.getUserAction().getActionName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

        dateAction.setText(formatter.format(coupleActionDate.getDateActuelle()));
    }

}
