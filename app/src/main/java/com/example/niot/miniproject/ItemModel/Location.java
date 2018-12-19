package com.example.niot.miniproject.ItemModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lng")
    private double longitude;

    public Location(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
