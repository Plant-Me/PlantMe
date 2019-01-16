package com.plantme.plantme.model;

public class ActionCalendrier {


    private Integer idActionCalendrier;

    private String type;

    private Integer idMois;

    private String mois;

    /**
     * No args constructor for use in serialization
     *
     */
    public ActionCalendrier() {
    }

    /**
     *
     * @param idActionCalendrier
     * @param mois
     * @param idMois
     * @param type
     */
    public ActionCalendrier(Integer idActionCalendrier, String type, Integer idMois, String mois) {
        super();
        this.idActionCalendrier = idActionCalendrier;
        this.type = type;
        this.idMois = idMois;
        this.mois = mois;
    }

    public Integer getIdActionCalendrier() {
        return idActionCalendrier;
    }

    public void setIdActionCalendrier(Integer idActionCalendrier) {
        this.idActionCalendrier = idActionCalendrier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdMois() {
        return idMois;
    }

    public void setIdMois(Integer idMois) {
        this.idMois = idMois;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

}