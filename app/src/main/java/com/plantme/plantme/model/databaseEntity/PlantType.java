package com.plantme.plantme.model.databaseEntity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "plant_type")
public class PlantType implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int plantTypeId;
    String type;

    public PlantType(int plantTypeId, String type) {
        this.plantTypeId = plantTypeId;
        this.type = type;
    }

    public PlantType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlantTypeId() {
        return plantTypeId;
    }

    public void setPlantTypeId(int plantTypeId) {
        this.plantTypeId = plantTypeId;
    }
}
