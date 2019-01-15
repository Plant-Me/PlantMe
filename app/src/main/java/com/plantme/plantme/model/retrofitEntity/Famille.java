
package com.plantme.plantme.model.retrofitEntity;



public class Famille {


    private Integer idFamille;

    private String nom;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Famille() {
    }

    /**
     * 
     * @param idFamille
     * @param nom
     */
    public Famille(Integer idFamille, String nom) {
        super();
        this.idFamille = idFamille;
        this.nom = nom;
    }

    public Integer getIdFamille() {
        return idFamille;
    }

    public void setIdFamille(Integer idFamille) {
        this.idFamille = idFamille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
