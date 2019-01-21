package com.plantme.plantme.model.retrofitEntity;

public class ResponseLastId {

    private int lastId;

    public ResponseLastId(int lastId) {
        this.lastId = lastId;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }
}
