package com.plantme.plantme.model;

import java.util.List;

public class PlanteUtilisateur {

    private int id_utilisateur;
    private int id_plante;
    private String nom_personnel;

    public PlanteUtilisateur(int id_utilisateur, int id_plante, String nom_personnel) {
        this.id_utilisateur = id_utilisateur;
        this.id_plante = id_plante;
        this.nom_personnel = nom_personnel;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getId_plante() {
        return id_plante;
    }

    public void setId_plante(int id_plante) {
        this.id_plante = id_plante;
    }

    public String getNom_personnel() {
        return nom_personnel;
    }

    public void setNom_personnel(String nom_personnel) {
        this.nom_personnel = nom_personnel;
    }
}
