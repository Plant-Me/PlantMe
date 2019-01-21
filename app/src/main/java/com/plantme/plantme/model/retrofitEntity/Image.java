
package com.plantme.plantme.model.retrofitEntity;

public class Image {


    private Integer idImage;
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Image() {
    }

    /**
     * 
     * @param idImage
     * @param url
     */
    public Image(Integer idImage, String url) {
        super();
        this.idImage = idImage;
        this.url = url;
    }

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
