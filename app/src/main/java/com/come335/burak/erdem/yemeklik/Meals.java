package com.come335.burak.erdem.yemeklik;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class Meals extends AppCompatActivity{

    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> contents = new ArrayList<String>();
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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
        long maxCount = dataSnapshot.child("meal").getChildrenCount();
        Random rand = new Random();

        for(int i = 1; i <= maxCount; i++)
        {
            for (DataSnapshot ds : dataSnapshot.getChildren())
            {
                int randomId = rand.nextInt((int) maxCount);

                names.add(ds.child(String.valueOf(randomId)).getValue(SingleMeal.class).getName());
                contents.add(ds.child(String.valueOf(randomId)).getValue(SingleMeal.class).getContent());
                images.add(ds.child(String.valueOf(randomId)).getValue(SingleMeal.class).getPhotoURL());
            }
        }

        InitiateRecycleView();

    }

    private void InitiateRecycleView()
    {
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rAdapter = new RecyclerViewAdapter(this,names, contents, images);
        rView.setAdapter(rAdapter);
    }


    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
