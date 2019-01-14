package com.plantme.plantme.model;

import android.widget.ImageView;

public class ActionPlant {
    private String actionName;
    private ImageView actionImage;

//    public ActionPlant(String actionName, ImageView actionImage) {
//        this.actionName = actionName;
//        this.actionImage = actionImage;
//    }


    public ActionPlant(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ImageView getActionImage() {
        return actionImage;
    }

    public void setActionImage(ImageView actionImage) {
        this.actionImage = actionImage;
    }
}
