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

public class MainActivity extends AppCompatActivity {

    Button btnWhatToEat;
    Button btnShowMenu;
    Button btnLogOut;
    TextView tv_userInfo;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/


        btnWhatToEat = findViewById(R.id.btnWhatToEat);
        btnShowMenu = findViewById(R.id.btnShowMenu);
        btnLogOut = findViewById(R.id.btnLogOut);
        mAuth = FirebaseAuth.getInstance();
        tv_userInfo = findViewById(R.id.tv_userInfo);
        Intent getIntent = getIntent();
        tv_userInfo.setText("Logged in as: "+getIntent.getStringExtra("userInfo"));


////////////////////////////////////////////////////////////////////////////////////////////////////


        btnWhatToEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnShowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Meals.class);
//                intent.putExtra();
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
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
