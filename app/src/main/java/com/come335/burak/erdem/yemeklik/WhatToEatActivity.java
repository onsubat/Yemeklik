package com.come335.burak.erdem.yemeklik;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Created by Erdem on 23-Apr-18.
 */

public class WhatToEatActivity extends AppCompatActivity
{
    int randomMealId;
    long maxCount;

    private SensorManager sm;
    boolean shakeBool=true;

    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;

    private float acelVal;
    private float acelLast;
    private float shake;

    TextView tv_whatToEat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what_to_eat);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        shakeBool = true;
        tv_whatToEat = findViewById(R.id.tv_whatToEat);

        myRef.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                maxCount = dataSnapshot.child("meal").getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private final SensorEventListener sensorListener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if(shake > 12)
            {
                if(shakeBool == true)
                {
                    shakeBool = false;
                    tv_whatToEat.setText("Please go back");
                    Intent intent = new Intent(WhatToEatActivity.this, DetailsActivity.class);

                    Random rand = new Random();
                    randomMealId = rand.nextInt((int) maxCount);
                    intent.putExtra("id", randomMealId);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i)
        {

        }
    };

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}