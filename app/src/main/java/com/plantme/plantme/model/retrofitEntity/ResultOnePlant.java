
package com.plantme.plantme.model.retrofitEntity;

import java.util.List;

public class ResultOnePlant {


    private Integer idPlante;

    private String nomFr;

    private String nomLatin;

    private String description;

    private String couleurFleurs;

    private String exposition;

    private String sol;

    private String usageMilieu;

    private List<Type> type = null;

    private Image image;

    private Famille famille;

    private List<Action> actions = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResultOnePlant() {
    }

    /**
     * 
     * @param idPlante
     * @param exposition
     * @param description
     * @param usageMilieu
     * @param nomLatin
     * @param sol
     * @param image
     * @param nomFr
     * @param couleurFleurs
     * @param type
     * @param famille
     * @param actions
     */
    public ResultOnePlant(Integer idPlante, String nomFr, String nomLatin, String description, String couleurFleurs, String exposition, String sol, String usageMilieu, List<Type> type, Image image, Famille famille, List<Action> actions) {
        super();
        this.idPlante = idPlante;
        this.nomFr = nomFr;
        this.nomLatin = nomLatin;
        this.description = description;
        this.couleurFleurs = couleurFleurs;
        this.exposition = exposition;
        this.sol = sol;
        this.usageMilieu = usageMilieu;
        this.type = type;
        this.image = image;
        this.famille = famille;
        this.actions = actions;
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

    public String getNomLatin() {
        return nomLatin;
    }

    public void setNomLatin(String nomLatin) {
        this.nomLatin = nomLatin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouleurFleurs() {
        return couleurFleurs;
    }

    public void setCouleurFleurs(String couleurFleurs) {
        this.couleurFleurs = couleurFleurs;
    }

    public String getExposition() {
        return exposition;
    }

    public void setExposition(String exposition) {
        this.exposition = exposition;
    }

    public String getSol() {
        return sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public String getUsageMilieu() {
        return usageMilieu;
    }

    public void setUsageMilieu(String usageMilieu) {
        this.usageMilieu = usageMilieu;
    }

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Famille getFamille() {
        return famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getTypesToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Type types:type
             ) {
            if(type.iterator().hasNext()){
                stringBuilder.append("Type ").append(types.getIdType()).append(": ").append(types.getNom()).append(", ");
            }else {
                stringBuilder.append("Type ").append(types.getIdType()).append(": ").append(types.getNom());
            }

        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "ResultOnePlant{" +
                "idPlante=" + idPlante +
                ", nomFr='" + nomFr + '\'' +
                ", nomLatin='" + nomLatin + '\'' +
                ", description='" + description + '\'' +
                ", couleurFleurs='" + couleurFleurs + '\'' +
                ", exposition='" + exposition + '\'' +
                ", sol='" + sol + '\'' +
                ", usageMilieu='" + usageMilieu + '\'' +
                ", type=" + type +
                ", image=" + image +
                ", famille=" + famille +
                ", actions=" + actions +
                '}';
    }
}
