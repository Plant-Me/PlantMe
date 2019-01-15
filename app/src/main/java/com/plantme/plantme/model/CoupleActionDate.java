package com.plantme.plantme.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CoupleActionDate {
    private String plantName;
    private UserAction userAction;
    private Date date;
    private boolean isValidated = false;

    public CoupleActionDate(String plantName, UserAction userAction, Date date) {
        this.plantName = plantName;
        this.userAction = userAction;
        this.date = date;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public UserAction getUserAction() {
        return userAction;
    }

    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }
}
