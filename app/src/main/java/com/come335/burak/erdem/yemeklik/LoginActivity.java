package com.come335.burak.erdem.yemeklik;

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

    Button btnSignIn;
    Button btnSignOut;
    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



////////////////////////////////////////////////////////////////////////////////////////////////////
                                       /*Initializing Variables*/


        mAuth = FirebaseAuth.getInstance();
        btnSignIn =(Button) findViewById(R.id.btnSignIn);
        btnSignOut =(Button) findViewById(R.id.btnSignOut);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);



////////////////////////////////////////////////////////////////////////////////////////////////////

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if(!email.equals("") && !pass.equals("")){
                    mAuth.signInWithEmailAndPassword(email,pass);
                    toastMessage("Signed");
                }else{
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
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
            toastMessage("Successfully signed in with: " + currentUser.getEmail());
        } else {
            // User is signed out
            toastMessage("Successfully signed out.");
        }
/*
        updateUI(currentUser);
*/
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
