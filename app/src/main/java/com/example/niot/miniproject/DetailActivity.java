package com.example.niot.miniproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.niot.miniproject.ItemModel.PhoneNumber;
import com.example.niot.miniproject.ItemModel.PhoneNumberReponse;
import com.example.niot.miniproject.ItemModel.Place;
import com.example.niot.miniproject.PlaceAPI.PlacesApiService;
import com.example.niot.miniproject.PlaceAPI.RetrofitObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback{
    private final String TAG = "DetailActivity";

    private Place currentPlace;
    private GoogleMap mMap;
    private MapWrapperLayout mapWrapperLayout;

    private RatingBar ratingBarPlace;
    private TextView textViewPlaceName;
    private TextView textViewPlaceAddress;
    private TextView textViewPlaceOpeningHour;
    private TextView textViewPlacePhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        getPlace(bundle);

        setMap();
        setValueDetail();
    }

    private void setValueDetail() {
        getDetailViews();

        textViewPlaceName.setText(currentPlace.getName());
        ratingBarPlace.setRating(currentPlace.getRating());
        textViewPlaceAddress.setText(currentPlace.getAddress());
        if(currentPlace.getOpening_hours().getOpen_now()){
            textViewPlaceOpeningHour.setText("Open now");
        }else{
            textViewPlaceOpeningHour.setText("Close now");
        }
        loadJsonAndSetPhoneNumber();
        setPhoneNumberCall();
    }

    private void setPhoneNumberCall() {
        textViewPlacePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textViewPlacePhoneNumber.getText().toString().isEmpty()) {
                    String phoneNumber = String.format("tel:%s", textViewPlacePhoneNumber.getText().toString());
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);

                    dialIntent.setData(Uri.parse(phoneNumber));

                    if (dialIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(dialIntent);
                    }else{
                        Log.e(TAG,"Can not resolve ACTION_DIAL");
                    }
                }else{
                    Log.e(TAG,"Can not resolve ACTION_DIAL");
                }

            }
        });
    }

    private void loadJsonAndSetPhoneNumber() {
        try{
            PlacesApiService placesApiService = RetrofitObject.getRetrofit().create(PlacesApiService.class);
            Map<String,String> queryOptions = new HashMap<>();
            queryOptions.put("key",BuildConfig.THE_PLACE_API_TOKEN_KEY);
            queryOptions.put("fields","formatted_phone_number");
            queryOptions.put("placeid",currentPlace.getPlace_id());

            Call<PhoneNumberReponse> call = placesApiService.getPhoneNumber(queryOptions);

            call.enqueue(new Callback<PhoneNumberReponse>() {
                @Override
                public void onResponse(Call<PhoneNumberReponse> call, Response<PhoneNumberReponse> response) {
                    PhoneNumber phoneNumber = response.body().getPhoneNumber();
                    if(phoneNumber != null){
                        textViewPlacePhoneNumber.setText(phoneNumber.getPhone_number());
                    }
                }

                @Override
                public void onFailure(Call<PhoneNumberReponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    private void getDetailViews() {
        ratingBarPlace = findViewById(R.id.detail_rating_bar);
        textViewPlaceName= findViewById(R.id.detail_text_view_name_place);
        textViewPlaceAddress = findViewById(R.id.detail_text_view_address);
        textViewPlaceOpeningHour = findViewById(R.id.detail_text_view_opening_hour);
        textViewPlacePhoneNumber = findViewById(R.id.detail_text_view_phone_number);
    }

    private void setMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_detail);
        assert mapFragment != null;
        mapFragment.getMapAsync(DetailActivity.this);

        mapWrapperLayout = findViewById(R.id.map_wrapper_detail);
    }

    private void getPlace(Bundle bundle) {
        //Intent intent = getIntent();
        assert bundle != null;
        currentPlace = (Place) bundle.get("place");
    }

    @Override
    public void onMapLoaded() {
        checkForPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        checkForPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        mMap.setMyLocationEnabled(true);

        animateCameraToCurrentPosition();
    }
    private void checkForPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission)) {

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        1);
            }
        }
    }
    private void animateCameraToCurrentPosition(){
        LatLng currentLatLng = new LatLng(currentPlace.getGeometry().getLocation().getLatitude(),
                currentPlace.getGeometry().getLocation().getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLng)
                .zoom(16)
                .bearing(0)
                .tilt(10)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(currentLatLng);
        mMap.addMarker(markerOptions);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapWrapperLayout.init(mMap,DetailActivity.this);
        mMap.setOnMapLoadedCallback(DetailActivity.this);
    }


}
