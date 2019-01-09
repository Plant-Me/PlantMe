package com.plantme.plantme.model;

import java.io.Serializable;
import java.util.List;

public class Plant implements Serializable {
    private String frName;
    private String ltnName;
    private String flowerColor;
    private String type;
    private String exposition;
    private String ground;
    private String usage;
    private List<ActionType> actionType;

    public Plant() {

    }

    public Plant(String frName, String ltnName, String flowerColor, String type, String exposition, String ground, String usage) {
        this.frName = frName;
        this.ltnName = ltnName;
        this.flowerColor = flowerColor;
        this.type = type;
        this.exposition = exposition;
        this.ground = ground;
        this.usage = usage;
    }


    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getLtnName() {
        return ltnName;
    }

    public void setLtnName(String ltnName) {
        this.ltnName = ltnName;
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExposition() {
        return exposition;
    }

    public void setExposition(String exposition) {
        exposition = exposition;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        usage = usage;
    }

    public List<ActionType> getActionType() {
        return actionType;
    }

    public void setActionType(List<ActionType> actionType) {
        this.actionType = actionType;
    }
}
