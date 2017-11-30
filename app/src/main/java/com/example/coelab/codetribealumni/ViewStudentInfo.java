package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.data.Project;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewStudentInfo extends AppCompatActivity {
    private TextView names,gender,role,cellphone,email,location,year;
    private ListView projectList,experienceList;
    private DatabaseReference ref;
    private RecyclerView projectRecycler;
    private ArrayList<Project> listOfProjects;
    String id;
    FirebaseAuth auth;
    ProjectsAdapter adapter;
    String i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_profile);
        auth = FirebaseAuth.getInstance();
        projectRecycler = (RecyclerView) findViewById(R.id.recProjects);
        projectRecycler.setLayoutManager(new LinearLayoutManager(this));
        projectRecycler.setHasFixedSize(false);
        listOfProjects = new ArrayList<>();
        FirebaseUser user = auth.getCurrentUser();
        id = user.getUid();
        //Adding back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student information");

        //Person object
        Person p = (Person) getIntent().getSerializableExtra("Person");
        i = p.getId();
        ref = FirebaseDatabase.getInstance().getReference("Projects").child(i);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project p = dataSnapshot.getValue(Project.class);
                /*if(p != null ){
                    listOfProjects.add(0,p);
                    adapter = new ProjectsAdapter(listOfProjects,ViewStudentInfo.this);
                    projectRecycler.setAdapter(adapter);
                   adapter.notifyItemInserted(0);
                   projectRecycler.smoothScrollToPosition(0);
                }*/
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
        //finding views
        projectList = (ListView) findViewById(R.id.project_list);
        names = (TextView) findViewById(R.id.stNames);
        gender = (TextView) findViewById(R.id.stGender);
        role = (TextView) findViewById(R.id.stRole);
        cellphone = (TextView) findViewById(R.id.stCell);
        email = (TextView) findViewById(R.id.stEmail);
        location = (TextView) findViewById(R.id.stLocation);
        year = (TextView) findViewById(R.id.stYear);
        //Database reference
        if(p != null){
            names.setText(p.getName() + " " + p.getSurname());
            gender.setText(p.getGender());
            role.setText(p.getRole());
            cellphone.setText(p.getCell());
            email.setText(p.getEmail());
            location.setText(p.getLocation());
            year.setText(p.getYear());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), StudentActivity.class);
        startActivity(myIntent);
        return true;
    }
}
