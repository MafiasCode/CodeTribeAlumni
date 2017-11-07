package com.example.coelab.codetribealumni;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText email,password;
    String mail,pass;
    private Button login,forgotPass,createAccount;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login...");

        //FirebaseAuth
        auth = FirebaseAuth.getInstance();
        //finding views
        email = (EditText) findViewById(R.id.signInEmail);
        password = (EditText) findViewById(R.id.signInPassword);
        //buttons
        login = (Button) findViewById(R.id.btnSignIn);
        forgotPass = (Button) findViewById(R.id.btnForgotPassword);
        createAccount = (Button) findViewById(R.id.btnCreateAccount);
        //setting onclick
        login.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        createAccount.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            mail = email.getText().toString().trim();
            pass = password.getText().toString().trim();
            if(TextUtils.isEmpty(mail)){
                email.setError("Field cannot be empty!");
                email.setHintTextColor(Color.RED);
                return;
            }
            if(TextUtils.isEmpty(pass)){
                password.setError("Field cannot be empty!");
                password.setHintTextColor(Color.RED);
                return;
            }
            auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else if(view == forgotPass){

        }
        else if(view == createAccount){

        }
    }
}
