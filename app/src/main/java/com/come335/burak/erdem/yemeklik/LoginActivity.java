package com.come335.burak.erdem.yemeklik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Erdem on 22-Apr-18.
 */

public class LoginActivity extends AppCompatActivity{

    Button btnLogIn;
    Button btnLogOut;
    Button btnGoSignIn;
    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



////////////////////////////////////////////////////////////////////////////////////////////////////
                                       /*Initializing Variables*/


        mAuth = FirebaseAuth.getInstance();
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnGoSignIn = findViewById(R.id.btnGoSignIn);
        mEmail =  findViewById(R.id.email);
        mPassword =  findViewById(R.id.password);



////////////////////////////////////////////////////////////////////////////////////////////////////

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if(!email.equals("") && !pass.equals("")){
                    mAuth.signInWithEmailAndPassword(email,pass);
                    toastMessage("Logged in");
                }else{
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Logging Out...");
            }
        });

        btnGoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // User is signed in
            toastMessage("Successfully logged in with: " + currentUser.getEmail());
        } else {
            // User is signed out
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
