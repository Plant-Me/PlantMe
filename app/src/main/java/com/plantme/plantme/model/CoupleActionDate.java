package com.plantme.plantme.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CoupleActionDate implements Comparable<CoupleActionDate> {
    private UserPlant userPlant;
    private String plantName;
    private UserAction userAction;
    private Date dateActuelle;
    private Date dateInitiale;
    private String typeRepetition;
    private int valeurRepetition;
    private boolean validated;

    public CoupleActionDate(UserPlant userPlant, String plantName, UserAction userAction, Date dateActuelle) {
        this.plantName = plantName;
        this.userAction = userAction;
        this.dateActuelle = dateActuelle;
        typeRepetition = "";
        valeurRepetition = 0;
        this.userPlant = userPlant;
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
            return "tous les " + valeurRepetition + " " + typeRepetition;
        }
        else {
            return "";
        }
    }

    public void setRepetition(String typeRepetition, int valeurRepetition) {
        this.typeRepetition = typeRepetition;
        this.valeurRepetition = valeurRepetition;
    }

    public String getTypeRepetition() {
        return typeRepetition;
    }

    public int getValeurRepetition() {
        return valeurRepetition;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public UserPlant getUserPlant() {
        return userPlant;
    }

    public Date getDateInitiale() {
        return dateInitiale;
    }

    @Override
    public int compareTo(CoupleActionDate o) {
        if (o.getDateActuelle().after(getDateActuelle())) {
            return -1;
        } else if (o.getDateActuelle().before(getDateActuelle())) {
            return 1;
        } else {
            return 0;
        }
    }
}