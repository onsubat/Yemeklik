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
    private ArrayList<Integer> ids = new ArrayList<>();
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
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        rView = findViewById(R.id.rview2);

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
        FirebaseUser user = mAuth.getCurrentUser();

        long maxCount = dataSnapshot.child("users").child(user.getUid()).child("history").getChildrenCount();

        for(int i = 0; i < maxCount; i++)
        {
            for (DataSnapshot ds : dataSnapshot.getChildren())
            {
                UserHistory tempUser = new UserHistory();
                tempUser.setMealId(ds.child("users").child(user.getUid()).child("history").child(String.valueOf(i)).getValue(UserHistory.class).getMealId());

                ids.add(ds.child("meals").child(String.valueOf(tempUser.getMealId())).getValue(SingleMeal.class).getId());
                names.add(ds.child("meals").child(String.valueOf(tempUser.getMealId())).getValue(SingleMeal.class).getName());
                contents.add(ds.child("meals").child(String.valueOf(tempUser.getMealId())).getValue(SingleMeal.class).getContent());
                images.add(ds.child("meals").child(String.valueOf(tempUser.getMealId())).getValue(SingleMeal.class).getPhotoURL());
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
