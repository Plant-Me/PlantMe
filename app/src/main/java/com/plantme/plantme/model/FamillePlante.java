package com.plantme.plantme.model;

public class FamillePlante {
    private String nomFrancais;
    private String nomLatin;

    public FamillePlante(String nomFrancais, String nomLatin) {
        this.nomFrancais = nomFrancais;
        this.nomLatin = nomLatin;
    }

    public FamillePlante(String nomFrancais) {
        this.nomFrancais = nomFrancais;
    }

    public String getNomFrancais() {
        return nomFrancais;
    }

    public String getNomLatin() {
        return nomLatin;
    }

    public void setNomFrancais(String nomFrancais) {
        this.nomFrancais = nomFrancais;
    }

    public void setNomLatin(String nomLatin) {
        this.nomLatin = nomLatin;
    }
}
