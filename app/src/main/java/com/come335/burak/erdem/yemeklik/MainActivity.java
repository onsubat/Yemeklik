package com.come335.burak.erdem.yemeklik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnWhatToEat;
    Button btnShowAllMeals;
    Button btnLogOut;
    Button btnHistory;
    TextView tv_userInfo;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        btnHistory = findViewById(R.id.btnHistory);
        btnWhatToEat = findViewById(R.id.btnWhatToEat);
        btnShowAllMeals = findViewById(R.id.btnShowAllMeals);
        btnLogOut = findViewById(R.id.btnLogOut);
        tv_userInfo = findViewById(R.id.tv_userInfo);
        Intent getIntent = getIntent();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        tv_userInfo.setText("Logged in as: "+ user.getEmail());


////////////////////////////////////////////////////////////////////////////////////////////////////




        btnHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        btnWhatToEat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, WhatToEatActivity.class);
                startActivity(intent);
            }
        });

        btnShowAllMeals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Meals.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAuth.signOut();
                toastMessage("Logging Out...");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
