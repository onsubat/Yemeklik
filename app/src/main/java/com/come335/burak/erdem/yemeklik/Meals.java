package com.come335.burak.erdem.yemeklik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class Meals extends AppCompatActivity
{
    private  ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> contents = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<Float> ratings = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    RecyclerView rView;
    RecyclerView.Adapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_recyclerview);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        rView = findViewById(R.id.rview);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("meals");//ONEMLI!!!DATABASE HİYERARŞİSİNDE İLK ADIMI BURADA ATIYORUZ


////////////////////////////////////////////////////////////////////////////////////////////////////


        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                toastMessage(String.valueOf(dataSnapshot.child("1").getValue(SingleMeal.class).getName()));

                long maxCount = dataSnapshot.getChildrenCount();
                for(int i = 0; i < maxCount; i++)
                {
                    ids.add(dataSnapshot.child(String.valueOf(i)).getValue(SingleMeal.class).getId());
                    names.add(dataSnapshot.child(String.valueOf(i)).getValue(SingleMeal.class).getName());
                    contents.add(dataSnapshot.child(String.valueOf(i)).getValue(SingleMeal.class).getContent());
                    images.add(dataSnapshot.child(String.valueOf(i)).getValue(SingleMeal.class).getPhotoURL());
                    ratings.add(dataSnapshot.child(String.valueOf(i)).getValue(SingleMeal.class).getRating());
                }

                InitiateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }

    private void InitiateRecyclerView()
    {
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rAdapter = new RecyclerViewAdapter(this, ids, names, contents, images, ratings);
        rView.setAdapter(rAdapter);
    }


    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
