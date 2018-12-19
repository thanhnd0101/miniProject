package com.example.niot.miniproject.ItemModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhoneNumberReponse implements Serializable {
    @SerializedName("result")
    private PhoneNumber phoneNumber;

    public PhoneNumberReponse(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
