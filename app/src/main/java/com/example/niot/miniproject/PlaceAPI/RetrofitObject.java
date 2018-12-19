package com.example.niot.miniproject.PlaceAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {
    private static final String BASE_GOOGLE_API_URL = "https://maps.googleapis.com/maps/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_GOOGLE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
