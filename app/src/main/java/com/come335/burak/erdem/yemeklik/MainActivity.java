package com.come335.burak.erdem.yemeklik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnWhatToEat;
    Button btnShowMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/


        btnWhatToEat = (Button)findViewById(R.id.btnWhatToEat);
        btnShowMenu = (Button)findViewById(R.id.btnShowMenu);


////////////////////////////////////////////////////////////////////////////////////////////////////


        btnWhatToEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnShowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Meals.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
    }
}
