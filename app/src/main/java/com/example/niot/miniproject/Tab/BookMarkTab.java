package com.example.niot.miniproject.Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.niot.miniproject.Adapter.RecycleViewPlacesAdapter;
import com.example.niot.miniproject.Interface.RecylerViewClickListener;
import com.example.niot.miniproject.ItemModel.Geometry;
import com.example.niot.miniproject.ItemModel.Location;
import com.example.niot.miniproject.ItemModel.Place;
import com.example.niot.miniproject.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class BookMarkTab extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;
    private View view;

    private List<Place> bookmarked_places_list;
    private RecylerViewClickListener mListener;

    public BookMarkTab(Context context, RecylerViewClickListener listener,List<Place> book_masks) {
        this.mContext = context;
        this.mListener = listener;
        this.bookmarked_places_list = book_masks;
    }

    public static BookMarkTab newBookMarkTabInstance(Context context,RecylerViewClickListener listener, List<Place> book_masks) {
        BookMarkTab bookMarkTab = new BookMarkTab(context,listener,book_masks);
        Bundle args = new Bundle();
        bookMarkTab.setArguments(args);
        bookMarkTab.setRetainInstance(true);
        return bookMarkTab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_mark_listview, container, false);
        getMyView();
        loadRecyclerView();

        return view;
    }

    private void getMyView() {
        recyclerView = view.findViewById(R.id.book_mark_recyclerview);
        setRecyclerLayoutManager();
    }

    public void addBookMarkedPlace(Place newBookMarkPlace) {
        if (!bookmarked_places_list.contains(newBookMarkPlace)) {
            this.bookmarked_places_list.add(newBookMarkPlace);
            loadRecyclerView();
        }
    }

    private void loadRecyclerView() {
        RecycleViewPlacesAdapter recycleViewPlacesAdapter = new RecycleViewPlacesAdapter(mContext, bookmarked_places_list, mListener);
        recyclerView.setAdapter(recycleViewPlacesAdapter);
    }

    private void setRecyclerLayoutManager() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public List<Place> getBookmarked_places_list() {
        return bookmarked_places_list;
    }
}
