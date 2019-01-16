/*package com.plantme.plantme.model;

import com.plantme.plantme.model.databaseEntity.ActionCalendrier;
import com.plantme.plantme.model.databaseEntity.PlantType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plant implements Serializable {

    private int plantId;
    private String imageUrl;
    private String frName;
    private String ltnName;
    private FamillePlante famillePlante;
    private String description;
    private String flowerColor;
    private List<PlantType> type;
    private String exposition;
    private String ground;
    private String usage;
    private String description;
    private String family;
    private List<ActionCalendrier> actionCalendrier;

    public Plant() {

    }

    public Plant(String frName, String ltnName, String flowerColor, List<PlantType> type, String exposition, String ground, String usage) {
        this.frName = frName;
        this.ltnName = ltnName;
        this.famillePlante = famillePlante;
        this.description = description;
        this.flowerColor = flowerColor;
        this.type = type;
        this.exposition = exposition;
        this.ground = ground;
        this.usage = usage;
        this.actionCalendrierList = new ArrayList<>();
    }

    public Plant(int plantId, String imageUrl, String frName, String ltnName, String flowerColor, List<PlantType> type, String exposition, String ground, String usage, List<ActionCalendrier> actionCalendrier) {
        this.plantId = plantId;
        this.imageUrl = imageUrl;
        this.frName = frName;
        this.ltnName = ltnName;
        this.flowerColor = flowerColor;
        this.type = type;
        this.exposition = exposition;
        this.ground = ground;
        this.usage = usage;
        this.actionCalendrier = actionCalendrier;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
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

    public String getDescription() {
        return description;
    }

    public FamillePlante getFamillePlante() {
        return famillePlante;
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public List<PlantType> getType() {
        return type;
    }

    public void setType(List<PlantType> type) {
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

    public List<ActionCalendrier> getActionCalendrier() {
        return actionCalendrierList;
    }

    public void setActionCalendrier(List<ActionCalendrier> actionCalendrier) {
        this.actionCalendrierList = actionCalendrier;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void addActionCalendrier(ActionCalendrier actionCalendrier) {
        this.actionCalendrierList.add(actionCalendrier);
    }
}
*/