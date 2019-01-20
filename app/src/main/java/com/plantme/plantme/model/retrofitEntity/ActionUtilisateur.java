package com.plantme.plantme.model.retrofitEntity;

import java.util.Date;

/**
 * Created by lpiem on 19/01/2019.
 */

public class ActionUtilisateur {

    private Integer idActionUtilisateur;

    private String nomAction;

    private Date date;

    private Date dateInitiale;

    private String typeRepetition;

    private int valeurRepetition;



    public ActionUtilisateur(Integer idActionUtilisateur, String nomAction, Date date, Date dateInitiale, String typeRepetition, int valeurRepetition) {
        this.idActionUtilisateur = idActionUtilisateur;
        this.nomAction = nomAction;
        this.date = date;
        this.dateInitiale = dateInitiale;
        this.typeRepetition = typeRepetition;
        this.valeurRepetition = valeurRepetition;
    }

    public ActionUtilisateur(Integer idActionUtilisateur, String nomAction) {
        this.idActionUtilisateur = idActionUtilisateur;
        this.nomAction = nomAction;
    }

    public ActionUtilisateur(Integer idActionUtilisateur, String nomAction, Date date) {
        this.idActionUtilisateur = idActionUtilisateur;
        this.nomAction = nomAction;
        this.date = date;
    }


    public Integer getIdActionUtilisateur() {
        return idActionUtilisateur;
    }

    public void setIdActionUtilisateur(Integer idActionUtilisateur) {
        this.idActionUtilisateur = idActionUtilisateur;
    }

    public String getNomAction() {
        return nomAction;
    }

    public void setNomAction(String nomAction) {
        this.nomAction = nomAction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateInitiale() {
        return dateInitiale;
    }

    public void setDateInitiale(Date dateInitiale) {
        this.dateInitiale = dateInitiale;
    }

    public String getTypeRepetition() {
        return typeRepetition;
    }

    public void setTypeRepetition(String typeRepetition) {
        this.typeRepetition = typeRepetition;
    }

    public int getValeurRepetition() {
        return valeurRepetition;
    }

    public void setValeurRepetition(int valeurRepetition) {
        this.valeurRepetition = valeurRepetition;
    }


}
