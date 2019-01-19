package com.plantme.plantme.model.retrofitEntity;

import java.util.Date;

/**
 * Created by lpiem on 19/01/2019.
 */

public class ActionUtilisateur {

    private Integer idActionutilisateur;

    private String nomAction;

    private Date date;



    public ActionUtilisateur(Integer idActionutilisateur, String nomAction) {
        this.idActionutilisateur = idActionutilisateur;
        this.nomAction = nomAction;
    }

    public ActionUtilisateur(Integer idActionutilisateur, String nomAction, Date date) {
        this.idActionutilisateur = idActionutilisateur;
        this.nomAction = nomAction;
        this.date = date;
    }

    public Integer getIdActionutilisateur() {
        return idActionutilisateur;
    }

    public void setIdActionutilisateur(Integer idActionutilisateur) {
        this.idActionutilisateur = idActionutilisateur;
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
}
