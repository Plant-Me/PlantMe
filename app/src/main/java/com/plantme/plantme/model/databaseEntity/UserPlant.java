package com.plantme.plantme.model.databaseEntity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "user_plant")
public class UserPlant implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int userPlantId;

    private int userId;
    private int plantId;
    private String plantName;
    private String imageUrl;
    private String frName;
    private String ltnName;
    private String flowerColor;
    private List<PlantType> type;
    private String exposition;
    private String ground;
    private String usage;
    private String description;
    private String family;
    private List<ActionCalendrier> actionCalendrier;
    private List<CoupleActionDate> listCoupleActionDate;

    public UserPlant(int userId, int plantId, String plantName, String imageUrl, String frName, String ltnName, String flowerColor, List<PlantType> type, String exposition, String ground, String usage, String description, String family, List<ActionCalendrier> actionCalendrier, List<CoupleActionDate> listCoupleActionDate) {
        this.userId = userId;
        this.plantId = plantId;
        this.plantName = plantName;
        this.imageUrl = imageUrl;
        this.frName = frName;
        this.ltnName = ltnName;
        this.flowerColor = flowerColor;
        this.type = type;
        this.exposition = exposition;
        this.ground = ground;
        this.usage = usage;
        this.description = description;
        this.family = family;
        this.actionCalendrier = actionCalendrier;
        this.listCoupleActionDate = listCoupleActionDate;
    }

    public UserPlant(int userId, int plantId, String plantName, List<CoupleActionDate> listCoupleActionDate) {
        this.userId = userId;
        this.plantId = plantId;
        this.plantName = plantName;
        this.listCoupleActionDate = listCoupleActionDate;
    }

    public UserPlant(int userId, int plantId) {
        this.userId = userId;
        this.plantId = plantId;

    }

    public UserPlant(int userId, int plantId, List<CoupleActionDate> listCoupleActionDate) {
        this.userId = userId;
        this.plantId = plantId;
        this.listCoupleActionDate = listCoupleActionDate;
    }

    public UserPlant(int userId, int plantId, String plantName) {
        this.userId = userId;
        this.plantId = plantId;
        this.plantName = plantName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public List<ActionCalendrier> getActionCalendrier() {
        return actionCalendrier;
    }

    public void setActionCalendrier(List<ActionCalendrier> actionCalendrier) {
        this.actionCalendrier = actionCalendrier;
    }

    public int getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(int userPlantId) {
        this.userPlantId = userPlantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public List<CoupleActionDate> getListCoupleActionDate() {
        return listCoupleActionDate;
    }

    public void setListCoupleActionDate(List<CoupleActionDate> listCoupleActionDate) {
        this.listCoupleActionDate = listCoupleActionDate;
    }
}
