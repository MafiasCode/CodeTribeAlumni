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
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.pojo.Accomplishments;
import com.example.coelab.codetribealumni.adapter.AccomplishmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccomplishmentActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView accomplist;
    private FloatingActionButton addAccomplishment;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ArrayList<Accomplishments> accList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomplishment);
        getSupportActionBar().setTitle("ACCOMPLISHMENTS");
        accomplist = findViewById(R.id.accomplist);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Accomplishments");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Accomplishments acc = dataSnapshot.getValue(Accomplishments.class);
                if(acc != null){
                    accList.add(acc);
                   AccomplishmentAdapter adapter =  new AccomplishmentAdapter(getApplicationContext(),accList);
                    accomplist.setAdapter(adapter);
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
        addAccomplishment = (FloatingActionButton) findViewById(R.id.addAccomp);
        addAccomplishment.setOnClickListener(this);
        accomplist = (ListView) findViewById(R.id.accomplist);


    }

    @Override
    public void onClick(View v){
        if(v == addAccomplishment){
            startActivity(new Intent(getApplicationContext(),AddAccomplishment.class));
        }
    }



}
