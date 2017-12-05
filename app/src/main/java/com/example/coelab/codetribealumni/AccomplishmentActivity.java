package com.example.coelab.codetribealumni;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.pojo.Accomplishments;
import com.example.coelab.codetribealumni.adapter.AccomplishmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccomplishmentActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView accomplist;
    private FloatingActionButton fab_add;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    AccomplishmentAdapter adapter;
    String id;
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
        fab_add = (FloatingActionButton) findViewById(R.id.addAccomp);
        fab_add.setOnClickListener(this);
        accomplist = (ListView) findViewById(R.id.accomplist);


    }

    @Override
    public void onClick(View v){
        if(v == fab_add){
           startActivity(new Intent(getApplicationContext(),AddAccomplishment.class));

           /* View view = LayoutInflater.from(getParent()).inflate(R.layout.second_dialog_layout,null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(getParent());
            dialog.setView(view);
            dialog.setTitle("ADD Accomplishment");
            final EditText courseName = (EditText) view.findViewById(R.id.dialogCourseName);
            final EditText crsQualificaton = (EditText) view.findViewById(R.id.dialogQualification);
            final EditText crsinstitution = (EditText) view.findViewById(R.id.dialogInstitution);
            final EditText crsYear = (EditText) view.findViewById(R.id.dialogYear);
            dialog.setCancelable(true).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String c_name = courseName.getText().toString().trim();
                    String c_qualifacation = crsQualificaton.getText().toString().trim();
                    String c_institution = crsinstitution.getText().toString().trim();
                    String c_year = crsYear.getText().toString().trim();
                    String ids = ref.push().getKey();
                    Accomplishments acc = new Accomplishments(id,c_name,c_qualifacation,c_institution,c_year);
                    ref.child(ids).setValue(acc);
                }
            });

            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            Dialog d = dialog.create();
            d.show();;*/



        }
    }



}
