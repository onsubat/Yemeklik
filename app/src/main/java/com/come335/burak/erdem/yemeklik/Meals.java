package com.come335.burak.erdem.yemeklik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class Meals extends AppCompatActivity{

    private ArrayList<Integer> images = new ArrayList<Integer>();
    private ArrayList<String>  names = new ArrayList<String>();

    RecyclerView rView;
    RecyclerView.Adapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_recyclerview);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        images.add(R.drawable.iskender);
        images.add(R.drawable.manti);
        images.add(R.drawable.iskender);
        images.add(R.drawable.manti);

        names.add("iskender");
        names.add("manti");
        names.add("iskender");
        names.add("manti");


        rView = findViewById(R.id.rview);
        rAdapter = new RecyclerViewAdapter(this, images, names);
        rView.setAdapter(rAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);



////////////////////////////////////////////////////////////////////////////////////////////////////

    }

}
