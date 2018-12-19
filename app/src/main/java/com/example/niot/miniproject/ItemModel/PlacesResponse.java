package com.example.niot.miniproject.ItemModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlacesResponse implements Serializable {
    @SerializedName("results")
    private List<Place> places;
    @SerializedName("status")
    private String status;

    public PlacesResponse(List<Place> places,String status) {
        this.places = places;
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public String getStatus() {
        return status;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
