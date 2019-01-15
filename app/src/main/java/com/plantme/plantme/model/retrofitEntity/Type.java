
package com.plantme.plantme.model.retrofitEntity;



public class Type {


    private Integer idType;

    private String nom;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Type() {
    }

    /**
     * 
     * @param idType
     * @param nom
     */
    public Type(Integer idType, String nom) {
        super();
        this.idType = idType;
        this.nom = nom;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
