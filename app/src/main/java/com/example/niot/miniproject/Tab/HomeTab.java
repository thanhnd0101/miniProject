package com.example.niot.miniproject.Tab;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.niot.miniproject.Adapter.RecycleViewPlacesAdapter;
import com.example.niot.miniproject.BuildConfig;
import com.example.niot.miniproject.Interface.RecylerViewClickListener;
import com.example.niot.miniproject.ItemModel.Place;
import com.example.niot.miniproject.ItemModel.PlacesResponse;
import com.example.niot.miniproject.PlaceAPI.PlacesApiService;
import com.example.niot.miniproject.PlaceAPI.RetrofitObject;
import com.example.niot.miniproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class HomeTab extends Fragment{
    private final String TAG = "HomeTab";

    private ProgressDialog pd;

    private List<Place> places_list;

    private Context mContext;
    private RecyclerView recyclerView;
    private RecycleViewPlacesAdapter recycleViewPlacesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private RecylerViewClickListener mListener;
    private SearchView searchView;
    Map<String, String> queryOptions;

    public HomeTab(Context context, RecylerViewClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public static HomeTab newHomeTabInstance(Context context, RecylerViewClickListener listener) {
        HomeTab homeTabFragment = new HomeTab(context,listener);
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        homeTabFragment.setRetainInstance(true);
        return homeTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main, container, false);
        getMyView();

        queryOptions = new HashMap<>();
        queryOptions.put("key", BuildConfig.THE_PLACE_API_TOKEN_KEY);
        queryOptions.put("query", "tourism+places+in+ho+chi+minh+city");

        initPlacesView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getMyView() {
        recyclerView = view.findViewById(R.id.main_places_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_main);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initPlacesView();
            }
        });
        searchView = view.findViewById(R.id.main_searchView);
        searchView.setQueryHint("Search for places ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                if(s.length() != 0){
                    s = s.replaceAll(" ","+");
                    Toast.makeText(mContext,"searchview click "+s,Toast.LENGTH_LONG).show();
                    queryOptions.clear();
                    queryOptions = new HashMap<>();
                    queryOptions.put("key", BuildConfig.THE_PLACE_API_TOKEN_KEY);
                    queryOptions.put("query", s);
                    initPlacesView();
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initPlacesView() {

        if (!isOnline()) {
            Toast.makeText(mContext, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
            return;
        }
        pd = new ProgressDialog(mContext);
        pd.setMessage("Fetching data from google api....");
        pd.setCancelable(false);
        pd.show();
        setRecyclerLayoutManager();

        loadJson();
    }

    private void loadJson() {
        try {
            PlacesApiService placesApiService = RetrofitObject.getRetrofit().create(PlacesApiService.class);

            //Map<String, String> queryOptions = new HashMap<>();
            /*queryOptions.put("key", BuildConfig.THE_PLACE_API_TOKEN_KEY);
            queryOptions.put("query", "tourism+places+in+ho+chi+minh+city");*/
            Call<PlacesResponse> call = placesApiService.getPlaces(queryOptions);
            Log.e(TAG, call.request().url().toString());

            call.enqueue(new Callback<PlacesResponse>() {
                @Override
                public void onResponse(@NonNull Call<PlacesResponse> call, @NonNull Response<PlacesResponse> response) {
                    assert response.body() != null;
                    places_list = response.body().getPlaces();
                    if(places_list != null) {
                        recycleViewPlacesAdapter = new RecycleViewPlacesAdapter(mContext.getApplicationContext(), places_list, mListener);
                        recyclerView.setAdapter(recycleViewPlacesAdapter);
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        pd.dismiss();
                        if (!places_list.isEmpty()) {
                            Toast.makeText(mContext, "Places Refreshed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "You have exceeded your daily request quota for this API\nPlease try another time.", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(mContext, "There some error in requesting Google API\nPlease try another time.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PlacesResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, t.getMessage());
                    Toast.makeText(mContext, "Error Fetching places data", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void setRecyclerLayoutManager() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public Place getPlace(int position){
        return places_list.get(position);
    }
}

