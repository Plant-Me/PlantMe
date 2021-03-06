package com.plantme.plantme.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plant implements Serializable {
    private int idPlant;
    private String imageUrl;
    private String frName;
    private String ltnName;
    private FamillePlante famillePlante;
    private String description;
    private String flowerColor;
    private String type;
    private String exposition;
    private String ground;
    private String usage;
    private List<ActionCalendrier> actionCalendrierList;

    public Plant() {

    }


    public Plant(int idPlant, String imageUrl, String frName, String ltnName, FamillePlante famillePlante, String description, String flowerColor, String type, String exposition, String ground, String usage, List<ActionCalendrier> actionCalendrierList) {
        this.idPlant = idPlant;
        this.imageUrl = imageUrl;
        this.frName = frName;
        this.ltnName = ltnName;
        this.famillePlante = famillePlante;
        this.description = description;
        this.flowerColor = flowerColor;
        this.type = type;
        this.exposition = exposition;
        this.ground = ground;
        this.usage = usage;
        this.actionCalendrierList = actionCalendrierList;
    }

    public Plant(String frName, String ltnName, FamillePlante famillePlante, String description, String flowerColor, String type, String exposition, String ground, String usage) {
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

    public String getType() {
        return type;
    }

    public void setFamillePlante(FamillePlante famillePlante) {
        this.famillePlante = famillePlante;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getExposition() {
        return exposition;
    }

    public void setExposition(String exposition) {
        this.exposition = exposition;
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
        this.usage = usage;
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

    public int getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }

    public List<ActionCalendrier> getActionCalendrierList() {
        return actionCalendrierList;
    }

    public void setActionCalendrierList(List<ActionCalendrier> actionCalendrierList) {
        this.actionCalendrierList = actionCalendrierList;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "imageUrl='" + imageUrl + '\'' +
                ", frName='" + frName + '\'' +
                ", ltnName='" + ltnName + '\'' +
                ", famillePlante=" + famillePlante +
                ", description='" + description + '\'' +
                ", flowerColor='" + flowerColor + '\'' +
                ", type='" + type + '\'' +
                ", exposition='" + exposition + '\'' +
                ", ground='" + ground + '\'' +
                ", usage='" + usage + '\'' +
                ", actionCalendrierList=" + actionCalendrierList +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return idPlant == ((Plant)obj).getIdPlant();
    }
}
