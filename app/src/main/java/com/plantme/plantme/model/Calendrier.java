package com.plantme.plantme.model;

public class Calendrier {
    private final String actionName;
    private boolean janvier;
    private boolean fevrier;
    private boolean mars;
    private boolean avril;
    private boolean mai;
    private boolean juin;
    private boolean juillet;
    private boolean aout;
    private boolean septembre;
    private boolean octobre;
    private boolean novembre;
    private boolean decembre;

    public Calendrier(String actionName) {
        this.actionName = actionName;
        janvier = false;
        fevrier = false;
        mars = false;
        avril = false;
        mai = false;
        juin = false;
        juillet = false;
        aout = false;
        septembre = false;
        octobre = false;
        novembre = false;
        decembre = false;
    }

    public void setJanvier(boolean janvier) {
        this.janvier = janvier;
    }

    public void setFevrier(boolean fevrier) {
        this.fevrier = fevrier;
    }

    public void setMars(boolean mars) {
        this.mars = mars;
    }

    public void setAvril(boolean avril) {
        this.avril = avril;
    }

    public void setMai(boolean mai) {
        this.mai = mai;
    }

    public void setJuin(boolean juin) {
        this.juin = juin;
    }

    public void setJuillet(boolean juillet) {
        this.juillet = juillet;
    }

    public void setAout(boolean aout) {
        this.aout = aout;
    }

    public void setSeptembre(boolean septembre) {
        this.septembre = septembre;
    }

    public void setOctobre(boolean octobre) {
        this.octobre = octobre;
    }

    public void setNovembre(boolean novembre) {
        this.novembre = novembre;
    }

    public void setDecembre(boolean decembre) {
        this.decembre = decembre;
    }

    public String getActionName() {
        return actionName;
    }

    public boolean isJanvier() {
        return janvier;
    }

    public boolean isFevrier() {
        return fevrier;
    }

    public boolean isMars() {
        return mars;
    }

    public boolean isAvril() {
        return avril;
    }

    public boolean isMai() {
        return mai;
    }

    public boolean isJuin() {
        return juin;
    }

    public boolean isJuillet() {
        return juillet;
    }

    public boolean isAout() {
        return aout;
    }

    public boolean isSeptembre() {
        return septembre;
    }

    public boolean isOctobre() {
        return octobre;
    }

    public boolean isNovembre() {
        return novembre;
    }

    public boolean isDecembre() {
        return decembre;
    }
}
