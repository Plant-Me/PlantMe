package com.plantme.plantme.viewHolder;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.GlideApp;

import java.io.InputStream;

public class ActionCalendarViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivActionImage;
    private TextView tvMyPlantName;
    private TextView tvActionName;
    private View view;

    public ActionCalendarViewHolder(View viewItem) {
        super(viewItem);

        ivActionImage = viewItem.findViewById(R.id.ivActionImage);
        tvMyPlantName = viewItem.findViewById(R.id.tvMyPlantName);
        tvActionName = viewItem.findViewById(R.id.tvActionName);
        this.view = viewItem;
    }

    public void bind(CoupleActionDate coupleActionDate) {
        //ivActionImage.setactionPlant.getActionImage());
        tvMyPlantName.setText(coupleActionDate.getPlantName());
        tvActionName.setText(coupleActionDate.getUserAction().getActionName());

        GlideApp.with(view).load(coupleActionDate.getUserAction().getImageRId()).into(ivActionImage);
    }
}
