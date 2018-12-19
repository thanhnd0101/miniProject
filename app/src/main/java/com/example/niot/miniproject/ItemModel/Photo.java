package com.example.niot.miniproject.ItemModel;

import com.example.niot.miniproject.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo implements Serializable {
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;
    @SerializedName("photo_reference")
    private String photo_reference;

    public Photo(int height, int width, String photo_reference) {
        this.height = height;
        this.width = width;
        this.photo_reference = photo_reference;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    //private String baseUrlPhoto_reference = "https://maps.googleapis.com/maps/api/place/photo?photoreference=";

    public String getPhoto_referencePath() {
        /*return "https://maps.googleapis.com/maps/api/place/photo?photoreference="
                +photo_reference+"&key=" + BuildConfig.THE_PLACE_API_TOKEN_KEY;*/
        return "https://maps.googleapis.com/maps/api/place/photo?photoreference="
                +photo_reference
                +"&maxwidth=700&key="+ BuildConfig.THE_PLACE_API_TOKEN_KEY;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }


}
