package com.plantme.plantme.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CoupleActionDate {
    private UserPlant userPlant;
    private String plantName;
    private UserAction userAction;
    private Date dateActuelle;
    private Date dateInitiale;
    private String typeRepetition;
    private int valeurRepetition;
    private SimpleDateFormat sdf;

    public CoupleActionDate(UserPlant userPlant, String plantName, UserAction userAction, Date dateActuelle) {
        this.userPlant = userPlant;
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
    }

    public CoupleActionDate(String plantName, UserAction userAction, Date dateActuelle) {
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
    }

    public CoupleActionDate(UserPlant userPlant, String plantName, UserAction userAction, Date dateActuelle, String typeRepetition, int valeurRepetition) {
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
        this.dateInitiale = dateActuelle;
        this.typeRepetition = typeRepetition;
        this.valeurRepetition = valeurRepetition;
        this.userPlant = userPlant;
    }

    public CoupleActionDate(UserPlant userPlant, String plantName, UserAction userAction, Date dateActuelle, Date dateInitiale, String typeRepetition, int valeurRepetition) {
        this.userPlant = userPlant;
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
        this.dateInitiale = dateInitiale;
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
        return typeRepetition + " (" + valeurRepetition + ")";
    }

    public UserPlant getUserPlant() {
        return userPlant;
    }

    public Date getDateInitiale() {
        return dateInitiale;
    }

    public String getTypeRepetition() {
        return typeRepetition;
    }

    public int getValeurRepetition() {
        return valeurRepetition;
    }
}
