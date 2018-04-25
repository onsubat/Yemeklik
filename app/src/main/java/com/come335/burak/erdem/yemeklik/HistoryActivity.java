package com.come335.burak.erdem.yemeklik;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Erdem on 23-Apr-18.
 */

public class HistoryActivity extends AppCompatActivity
{
    private ArrayList<Integer> ids = new ArrayList<>();// this ids is different from others. Storing the ids of eaten meals by user history, not meals from database.
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> contents = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Integer> givenRates = new ArrayList<>();
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user;

    RecyclerView rView;
    RecyclerView.Adapter rAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_recyclerview);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        rView = findViewById(R.id.rview2);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();



////////////////////////////////////////////////////////////////////////////////////////////////////

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                long maxCount = dataSnapshot.getChildrenCount();

                for (DataSnapshot ds : dataSnapshot.child("users").child(user.getUid()).child("history").getChildren())
                {
                    ids.add(ds.getValue(UserHistory.class).getMealId());
                    dates.add(ds.getValue(UserHistory.class).getDate());
                    givenRates.add(ds.getValue(UserHistory.class).getGivenRate());
                }

                for(int id:ids)//Extracting data from "meals" with the id of the meal.
                {
                    names.add(dataSnapshot.child("meals").child(String.valueOf(id)).getValue(SingleMeal.class).getName());
                    contents.add(dataSnapshot.child("meals").child(String.valueOf(id)).getValue(SingleMeal.class).getContent());
                    images.add(dataSnapshot.child("meals").child(String.valueOf(id)).getValue(SingleMeal.class).getPhotoURL());
                }

                RunRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }

    private void RunRecyclerView()
    {
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        rAdapter = new Recycler2(this, names, contents, images, dates, givenRates);
        rView.setAdapter(rAdapter);
    }


    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
