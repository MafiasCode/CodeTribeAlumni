package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class AccomplishmentActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton addAccomp;
    private ListView experienceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomplishment);

        addAccomp = (FloatingActionButton) findViewById(R.id.addExperience);
        addAccomp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v ==addAccomp){
            startActivity(new Intent(getApplicationContext(),AddAccomplishment.class));
        }
    }
}
