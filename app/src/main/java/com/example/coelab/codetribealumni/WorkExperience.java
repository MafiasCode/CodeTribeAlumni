package com.example.coelab.codetribealumni;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.pojo.Experience;
import com.example.coelab.codetribealumni.pojo.ExperienceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkExperience extends AppCompatActivity implements View.OnClickListener{
    private ListView experienceList;
    private FloatingActionButton addExperience;
    private DatabaseReference ref;
    private ArrayList<Experience> exList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);
        getSupportActionBar().setTitle("Employment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        experienceList = findViewById(R.id.xperienceList);
        ref = FirebaseDatabase.getInstance().getReference("Experience");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Experience ex = dataSnapshot.getValue(Experience.class);
                if(ex != null){
                    exList.add(ex);
                    ExperienceAdapter adapter = new ExperienceAdapter(getApplicationContext(),exList);
                    experienceList.setAdapter(adapter);
                    //Toast.makeText(WorkExperience.this, exList.get(0).getCompanyName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        addExperience = (FloatingActionButton) findViewById(R.id.addExperience);
        addExperience.setOnClickListener(this);
        experienceList = (ListView) findViewById(R.id.xperienceList);
    }
    @Override
    public void onClick(View v) {
        if(v == addExperience){
            startActivity(new Intent(getApplicationContext(),AddExperience.class));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), StudentActivity.class);
        startActivity(myIntent);
        return true;

    }
}
