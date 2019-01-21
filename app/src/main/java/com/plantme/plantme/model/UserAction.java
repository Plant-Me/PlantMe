package com.plantme.plantme.model;

public class UserAction {
    private int idAction;
    private int imageRId;
    private String actionName;

    public UserAction(String actionName) {
        this.actionName = actionName;
    }

    public UserAction(int idAction, String actionName) {
        this.idAction = idAction;
        this.actionName = actionName;
    }

    public UserAction(int idAction, int imageRId, String actionName) {
        this.idAction = idAction;
        this.imageRId = imageRId;
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getIdAction() {
        return idAction;
    }

    @Override
    public boolean equals(Object obj) {
        return idAction == ((UserAction)obj).getIdAction();
    }

    public int getImageRId() {
        return imageRId;
    }
}
