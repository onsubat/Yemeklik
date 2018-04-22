package com.come335.burak.erdem.yemeklik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Erdem on 22-Apr-18.
 */

public class LoginActivity extends AppCompatActivity{

    Button btnLogIn;
    Button btnGoToSignIn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mEmail, mPassword;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



////////////////////////////////////////////////////////////////////////////////////////////////////
                                       /*Initializing Variables*/


        mAuth = FirebaseAuth.getInstance();
        btnLogIn = findViewById(R.id.btnLogIn);
        btnGoToSignIn = findViewById(R.id.btnGoToSignIn);
        mEmail =  findViewById(R.id.email);
        mPassword =  findViewById(R.id.password);


////////////////////////////////////////////////////////////////////////////////////////////////////

        btnLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(!email.equals("") && !password.equals(""))
                {
                    LogIn(email, password);

                }
                else
                {
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        btnGoToSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            // User is signed in
            toastMessage("Successfully logged in with: " + currentUser.getEmail());
            TransferWithIntent(currentUser);
        }
        else
        {
            // User is signed out
        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void LogIn(String email, String password)
    {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            toastMessage("Logged in.");
                            TransferWithIntent(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            toastMessage("Authentication failed");
                        }

                        // ...
                    }
                });
    }

    private void TransferWithIntent(FirebaseUser user)
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        String userEmail = user.getEmail();
        intent.putExtra("userInfo", userEmail.toString());
        startActivity(intent);
    }
}
