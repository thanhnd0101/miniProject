package com.example.niot.miniproject.Viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niot.miniproject.Interface.RecylerViewClickListener;
import com.example.niot.miniproject.R;

public class PlaceViewHolder extends RecyclerView.ViewHolder{

    public ImageView thumbnailHeader;
    public Button bookmark;
    public Button favorite;
    public TextView category;
    public TextView place_name;

    private RecylerViewClickListener mListener;

    public PlaceViewHolder(@NonNull final View itemView, final RecylerViewClickListener listener) {
        super(itemView);
        this.mListener = listener;
        getMyView();
        setBookMarkButtonClick();
        setViewClick(itemView);
    }

    private void setViewClick(View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(itemView,position);
                    }
                }
            }
        });
    }

    private void setBookMarkButtonClick() {
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemBookMarkClick(position);
                    }
                }
            }
        });
    }

    private void getMyView() {
        thumbnailHeader = itemView.findViewById(R.id.item_place_imageview_image);
        bookmark = itemView.findViewById(R.id.item_place_button_bookmark);
        favorite = itemView.findViewById(R.id.item_place_button_favorite);
        category = itemView.findViewById(R.id.item_place_textview_category);
        place_name = itemView.findViewById(R.id.item_place_name);
    }

    public ImageView getThumbnailHeader() {
        return thumbnailHeader;
    }
}
