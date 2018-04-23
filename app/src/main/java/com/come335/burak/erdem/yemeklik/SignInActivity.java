package com.come335.burak.erdem.yemeklik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Erdem on 22-Apr-18.
 */

public class SignInActivity extends AppCompatActivity
{

    Button btnSignIn;
    EditText mEmail;
    EditText mPassword;
    private FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        mAuth = FirebaseAuth.getInstance();
        btnSignIn = findViewById(R.id.btnSignIn);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        ref = FirebaseDatabase.getInstance().getReference();


        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(!email.equals("") && !password.equals(""))
                {
                    //Sign in user
                    CheckCredentialsAndCreateAccount(view, email, password);
                }
                else
                {
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void CheckCredentialsAndCreateAccount(View v, final String email, final String password)
    {
        mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task)
            {
                boolean check = !task.getResult().getProviders().isEmpty();
                if(!check)
                {
                    if(password.length() < 6)
                    {
                        toastMessage("Password must have atleast 6 characters");
                    }
                    else
                    {
                        mAuth.createUserWithEmailAndPassword(email, password);
                        toastMessage("Account created successfully!");
                        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    toastMessage("That email address is already existing.");
                }
            }
        });
    }
}
