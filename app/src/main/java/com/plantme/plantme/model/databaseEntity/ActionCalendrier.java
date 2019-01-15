package com.plantme.plantme.model.databaseEntity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "action_calendrier")
public class ActionCalendrier implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String actionName;
    private int monthId;
    private String monthName;

    public ActionCalendrier(int id, String actionName, int monthId, String monthName) {
        this.id = id;
        this.actionName = actionName;
        this.monthId = monthId;
        this.monthName = monthName;
    }

    public ActionCalendrier(String actionName, int monthId, String monthName) {
        this.actionName = actionName;
        this.monthId = monthId;
        this.monthName = monthName;
    }

    public ActionCalendrier(int id, String actionName) {
        this.id = id;
        this.actionName = actionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
