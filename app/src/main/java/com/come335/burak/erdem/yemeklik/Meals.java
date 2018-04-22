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

/**
 * Created by Erdem on 21-Apr-18.
 */

public class Meals extends AppCompatActivity{

    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String>  names = new ArrayList<String>();
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
//        images.add(R.drawable.iskender);
//        images.add(R.drawable.manti);
//        images.add(R.drawable.iskender);
//        images.add(R.drawable.manti);

//        names.add("iskender");
//        names.add("manti");
//        names.add("iskender");
//        names.add("manti");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();


////////////////////////////////////////////////////////////////////////////////////////////////////

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

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
        long maxCount = dataSnapshot.getChildrenCount();
        long count = 0;
        toastMessage(String.valueOf(maxCount));
        SingleMeal meal = new SingleMeal();
        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            SingleMeal tempMeal = new SingleMeal();
            tempMeal.setName(ds.child("1").getValue(SingleMeal.class).getName());
            tempMeal.setContent(ds.child("1").getValue(SingleMeal.class).getContent());
            tempMeal.setPhotoURL(ds.child("1").getValue(SingleMeal.class).getPhotoURL());
            meal = tempMeal;
        }

        images.add(meal.getPhotoURL());
        names.add(meal.getName());
        InitiateRecycleView();

    }
    private void InitiateRecycleView()
    {
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rAdapter = new RecyclerViewAdapter(this, images, names);
        rView.setAdapter(rAdapter);
    }

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        if (mAuthListener != null)
//        {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
