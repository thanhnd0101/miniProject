package com.example.niot.miniproject.PlaceAPI;

import com.example.niot.miniproject.ItemModel.PhoneNumberReponse;
import com.example.niot.miniproject.ItemModel.PlacesResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PlacesApiService {
    @GET("api/place/textsearch/json")
    Call<PlacesResponse> getPlaces(@QueryMap Map<String,String> options);

    @GET("api/place/details/json")
    Call<PhoneNumberReponse> getPhoneNumber(@QueryMap Map<String,String> options);
}
