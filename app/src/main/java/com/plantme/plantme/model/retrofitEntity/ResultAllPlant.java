
package com.plantme.plantme.model.retrofitEntity;


import java.io.Serializable;

public class ResultAllPlant implements Serializable {


    private Integer idPlante;
    private String nomFr;
    private String usageMilieu;
    private Image image;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResultAllPlant() {
    }

    /**
     * 
     * @param idPlante
     * @param usageMilieu
     * @param image
     * @param nomFr
     */
    public ResultAllPlant(Integer idPlante, String nomFr, String usageMilieu, Image image) {
        super();
        this.idPlante = idPlante;
        this.nomFr = nomFr;
        this.usageMilieu = usageMilieu;
        this.image = image;
    }

    public Integer getIdPlante() {
        return idPlante;
    }

    public void setIdPlante(Integer idPlante) {
        this.idPlante = idPlante;
    }

    public String getNomFr() {
        return nomFr;
    }

    public void setNomFr(String nomFr) {
        this.nomFr = nomFr;
    }

    public String getUsageMilieu() {
        return usageMilieu;
    }

    public void setUsageMilieu(String usageMilieu) {
        this.usageMilieu = usageMilieu;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ResultAllPlant{" +
                "idPlante=" + idPlante +
                ", nomFr='" + nomFr + '\'' +
                ", usageMilieu='" + usageMilieu + '\'' +
                ", image=" + image +
                '}';
    }
}
