package com.example.niot.miniproject.ItemModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Place implements Serializable {
    @SerializedName("formatted_address")
    private String address;
    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("name")
    private String name;
    @SerializedName("opening_hours")
    private OpenNow opening_hours;
    @SerializedName("photos")
    private List<Photo> photos;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("rating")
    private float rating;
    @SerializedName("types")
    private List<String> categories;

    public Place(String address, Geometry geometry, String name,OpenNow opening_hours ,List<Photo> photos,String place_id, float rating, List<String> categories) {
        this.address = address;
        this.geometry = geometry;
        this.name = name;
        this.opening_hours = opening_hours;
        this.photos = photos;
        this.place_id=place_id;
        this.rating = rating;
        this.categories = categories;
    }

    public String getAddress() {
        return address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public float getRating() {
        return rating;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public OpenNow getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpenNow opening_hours) {
        this.opening_hours = opening_hours;
    }
}
