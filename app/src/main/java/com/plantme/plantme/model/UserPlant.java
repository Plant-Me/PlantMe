package com.plantme.plantme.model;

import java.util.List;

public class UserPlant {
    private Plant userPlant;
    private String plantName;
    private List<CoupleActionDate> listCoupleActionDate;


    public UserPlant(Plant userPlant, String plantName, List<CoupleActionDate> listCoupleActionDate) {
        this.userPlant = userPlant;
        this.plantName = plantName;
        this.listCoupleActionDate = listCoupleActionDate;
    }

    public Plant getUserPlant() {
        return userPlant;
    }

    public void setUserPlant(Plant userPlant) {
        this.userPlant = userPlant;
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
