package com.example.niot.miniproject.ItemModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OpenNow implements Serializable {
    @SerializedName("open_now")
    private Boolean open_now;

    public OpenNow(Boolean open_now) {
        this.open_now = open_now;
    }

    public void setOpen_now(Boolean open_now) {
        this.open_now = open_now;
    }

    public Boolean getOpen_now() {
        return open_now;
    }
}
