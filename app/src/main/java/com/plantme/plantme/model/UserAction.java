package com.plantme.plantme.model;

import java.util.Date;

public class UserAction {
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

}
