package com.example.niot.miniproject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.niot.miniproject.Interface.RecylerViewClickListener;
import com.example.niot.miniproject.ItemModel.Place;
import com.example.niot.miniproject.PlaceAPI.GlideBindingUtility;
import com.example.niot.miniproject.R;
import com.example.niot.miniproject.Viewholder.PlaceViewHolder;

import java.util.List;

public class RecycleViewPlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private RecylerViewClickListener mListener;
    private List<Place> place_list;

    public RecycleViewPlacesAdapter(Context mContext, List<Place> place_list, RecylerViewClickListener listener) {
        this.mListener = listener;
        this.mContext = mContext;
        this.place_list = place_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolderPlace;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View placeView = inflater.inflate(R.layout.item_place,viewGroup,false);
        viewHolderPlace = new PlaceViewHolder(placeView,mListener);

        return viewHolderPlace;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        PlaceViewHolder placeViewHolder = (PlaceViewHolder)viewHolder;
        configurePlaceViewHolder(placeViewHolder,position);
    }

    private void configurePlaceViewHolder(PlaceViewHolder placeViewHolder, int position) {
        Place place = place_list.get(position);
        if(place.getCategories() != null)placeViewHolder.category.setText(place.getCategories().get(0));
        if(place.getName()!=null) placeViewHolder.place_name.setText(place.getName());

        ImageView imageView = placeViewHolder.getThumbnailHeader();
        String imageUrl;
        if(place.getPhotos()!=null) {
            imageUrl = place.getPhotos().get(0).getPhoto_referencePath();
            //String imageUrl = "https://lh4.googleusercontent.com/-1wzlVdxiW14/USSFZnhNqxI/AAAAAAAABGw/YpdANqaoGh4/s1600-w400/Google%2BSydney";
            //String imageUrl = "http://vintourist.vn/wp-content/uploads/2018/01/Vinh-Ha-Long-2.jpg";
        }else{
            imageUrl = "http://img.cdn2.vietnamnet.vn/Images/english/2015/06/23/11/20150623112458-1.jpg";
        }
        GlideBindingUtility.loadImage(mContext.getApplicationContext(),imageView,imageUrl);
    }

    @Override
    public int getItemCount() {
        return place_list.size();
    }
}
