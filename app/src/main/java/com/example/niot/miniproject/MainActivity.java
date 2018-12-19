package com.example.niot.miniproject;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.niot.miniproject.Adapter.PagerAdapter;
import com.example.niot.miniproject.Interface.RecylerViewClickListener;
import com.example.niot.miniproject.ItemModel.Place;
import com.example.niot.miniproject.Tab.BookMarkTab;
import com.example.niot.miniproject.Tab.HomeTab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecylerViewClickListener {
    private final String TAG = "MainActivity";
    private final int HOME_TAB_POS = 0;
    private final int BOOKMARK_TAB_POS = 1;
    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getFilesDir().getPath(),"book_marks.dat");
        List<Place> book_marks = new ArrayList<>();
        try {
            FileInputStream fINT = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fINT);
            book_marks=(List<Place>)ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.main_viewpager);
        tabLayout = findViewById(R.id.main_tab_layout);
        pagerAdapter.addFragment(HomeTab.newHomeTabInstance(MainActivity.this, MainActivity.this), "Home");
        pagerAdapter.addFragment(BookMarkTab.newBookMarkTabInstance(MainActivity.this,MainActivity.this,book_marks), "Book Mark");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Place placeToPass = ((HomeTab) pagerAdapter.getFragment(HOME_TAB_POS)).getPlace(position);
        intent.putExtra("place", placeToPass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void onItemBookMarkClick(int position) {
        Toast.makeText(MainActivity.this, "Position " + position+" added to Bookmarks", Toast.LENGTH_SHORT).show();
        Place addedPlace = ((HomeTab) pagerAdapter.getFragment(HOME_TAB_POS)).getPlace(position);
        if (addedPlace != null) {
            ((BookMarkTab) pagerAdapter.getFragment(BOOKMARK_TAB_POS)).addBookMarkedPlace(addedPlace);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        List<Place> bookmark = ((BookMarkTab) pagerAdapter.getFragment(BOOKMARK_TAB_POS)).getBookmarked_places_list();
        File file = new File(getFilesDir().getPath(),"book_marks.dat");
        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fOut);
            oos.writeObject(bookmark);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
