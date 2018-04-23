package com.come335.burak.erdem.yemeklik;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    Button btnWhatToEat;
    Button btnShowMenu;

    private static final String TAG = "MainActivity";
    private static final int    ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    if(isServicesOK()){
        init();
    }
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

    private void init()
    {
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }
        });
    }

    public boolean isServicesOK()
    {
        Log.d(TAG, "isServicesOK: Checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available== ConnectionResult.SUCCESS)
        {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }

        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Log.d(TAG, "isServicesOK: An error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this , available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
            {
                Toast.makeText(this,"We can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
