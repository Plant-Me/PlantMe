package com.plantme.plantme.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CoupleActionDate {
    private String plantName;
    private UserAction userAction;
    private Date dateActuelle;
    private Date dateInitiale;
    private String typeRepetition;
    private int valeurRepetition;
    private SimpleDateFormat sdf;

    public CoupleActionDate(String plantName, UserAction userAction, Date dateActuelle) {
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
        typeRepetition = "";
        valeurRepetition = 0;
    }

    public CoupleActionDate(String plantName, UserAction userAction, Date dateActuelle, String typeRepetition, int valeurRepetition) {
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
        this.dateInitiale = dateActuelle;
        this.typeRepetition = typeRepetition;
        this.valeurRepetition = valeurRepetition;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public UserAction getUserAction() {
        return userAction;
    }

    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    public Date getDateActuelle() {
        return dateActuelle;
    }

    public void setDateActuelle(Date dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public String getRepetition() {
        if(typeRepetition != "" && valeurRepetition != 0) {
            return typeRepetition + " (" + valeurRepetition + ")";
        }
        else {
            return "";
        }
    }

    public void setRepetition(String typeRepetition, int valeurRepetition) {
        this.typeRepetition = typeRepetition;
        this.valeurRepetition = valeurRepetition;
    }
}
