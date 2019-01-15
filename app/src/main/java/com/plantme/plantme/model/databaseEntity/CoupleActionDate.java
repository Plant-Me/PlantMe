package com.plantme.plantme.model.databaseEntity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.plantme.plantme.model.UserAction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity(tableName = "couple_action_date")
public class CoupleActionDate implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int coupleActionDateId;

    private String plantName;
    private UserAction userAction;
    private Date date;


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
}
