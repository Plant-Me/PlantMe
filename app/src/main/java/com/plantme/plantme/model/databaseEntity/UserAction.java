package com.plantme.plantme.model.databaseEntity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "user_action")
public class UserAction {

    @PrimaryKey(autoGenerate = true)
    private int UserActionId;
    private String actionName;

    public UserAction(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public UserAction(int userActionId, String actionName) {
        UserActionId = userActionId;
        this.actionName = actionName;
    }

    public int getUserActionId() {
        return UserActionId;
    }

    public void setUserActionId(int userActionId) {
        UserActionId = userActionId;
    }
}
