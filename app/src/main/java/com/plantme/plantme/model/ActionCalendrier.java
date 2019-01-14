package com.plantme.plantme.model;

class ActionCalendrier {
    private int id;
    private String actionName;

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
}
