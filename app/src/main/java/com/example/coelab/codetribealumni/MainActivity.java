package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        //
    }

    //code to intent to the next screen depending on the pressed button
    public void sign_in(View view){

        //Intent intent = new Intent(this,SignInActivity.class);
        //startActivity(intent);
    }

    public void sign_up(View view){
        //Intent intent = new Intent(this,SignUpActivity.class);
        //startActivity(intent);
    }
}
