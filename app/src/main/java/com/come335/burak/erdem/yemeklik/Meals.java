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
    private FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


////////////////////////////////////////////////////////////////////////////////////////////////////


        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot)
    {
        long maxCount = dataSnapshot.child("meals").getChildrenCount();

        for(int i = 0; i < maxCount; i++)
        {
            for (DataSnapshot ds : dataSnapshot.getChildren())
            {
                ids.add(ds.child("meals").child(String.valueOf(i)).getValue(SingleMeal.class).getId());
                names.add(ds.child("meals").child(String.valueOf(i)).getValue(SingleMeal.class).getName());
                contents.add(ds.child("meals").child(String.valueOf(i)).getValue(SingleMeal.class).getContent());
                images.add(ds.child("meals").child(String.valueOf(i)).getValue(SingleMeal.class).getPhotoURL());
                ratings.add(ds.child("meals").child(String.valueOf(i)).getValue(SingleMeal.class).getRating());
            }
        }

        InitiateRecyclerView();
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
