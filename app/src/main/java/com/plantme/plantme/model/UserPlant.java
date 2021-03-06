package com.plantme.plantme.model;

import java.util.List;

public class UserPlant {
    private int id =0;
    private Plant plant;
    private String plantName;
    private List<CoupleActionDate> listCoupleActionDate;


    public UserPlant(Plant plant, String plantName, List<CoupleActionDate> listCoupleActionDate) {
        this.plant = plant;
        this.plantName = plantName;
        this.listCoupleActionDate = listCoupleActionDate;
    }

    public UserPlant(int id, Plant plant, String plantName, List<CoupleActionDate> listCoupleActionDate) {
        this.id = id;
        this.plant = plant;
        this.plantName = plantName;
        this.listCoupleActionDate = listCoupleActionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
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
