package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.pojo.Experience;
import com.example.coelab.codetribealumni.pojo.ExperienceAdapter;
import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {
    private TextView nams,gender,role,cellphone,email,location,year;
    private CollapsingToolbarLayout collapsing;
    private ProjectsAdapter adapter;
    private RecyclerView recProject,recExperience;
    private WorkExperienceAdapter wAdapter;
    String id;
    ArrayList<Experience> experienceList;
    ArrayList<Project> projectList;
    Toolbar toolbar;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ref = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapseActionView);
        collapsing = new CollapsingToolbarLayout(this);
        collapsing.setCollapsedTitleTypeface(Typeface.DEFAULT);
        recExperience = (RecyclerView) findViewById(R.id.recExperience);
        recExperience.setHasFixedSize(true);
        recExperience.setLayoutManager(new LinearLayoutManager(this));
        recProject = (RecyclerView) findViewById(R.id.recProjects);
        recProject.setHasFixedSize(true);
        recProject.setLayoutManager(new LinearLayoutManager(this));
        //getting intent
        Intent intent = getIntent();
        Person p = (Person) intent.getSerializableExtra("Person");
        id = p.getId();
        //viewData("Experience");
        //viewData("Projects");
        final Query projectQuery = ref.child("Projects").orderByChild("id").equalTo(id);
        projectQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                projectList = new ArrayList<>();
                if(dataSnapshot != null){
                    Project p = dataSnapshot.getValue(Project.class);
                    Toast.makeText(getApplicationContext(),p.getProjectName(),Toast.LENGTH_LONG).show();
                    projectList.add(p);
                    adapter = new ProjectsAdapter(projectList,StudentDetails.this);
                    recProject.setAdapter(adapter);
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
        //collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapseActionView);
//        collapsing.setTitle("Student details");
        //finding views
        nams = (TextView) findViewById(R.id.stNames);
        gender = (TextView) findViewById(R.id.stGender);
        role = (TextView) findViewById(R.id.stRole);
        cellphone = (TextView) findViewById(R.id.stCell);
        email = (TextView) findViewById(R.id.stEmail);
        location = (TextView) findViewById(R.id.stLocation);
        year = (TextView) findViewById(R.id.stYear);
        if(p != null){
            //nams.setText();
            gender.setText(p.getGender());
            role.setText(p.getRole());
            cellphone.setText(p.getCell());
            email.setText(p.getEmail());
            location.setText(p.getLocation());
            year.setText(p.getYear());
            toolbar.setTitle(p.getName() + " " + p.getSurname());

            //collapsing.setTitle(p.getName() + " " + p.getSurname());
        }
        final Query experienceQuery = ref.child("Experience").orderByChild("id").equalTo(id);
        experienceQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                experienceList = new ArrayList<>();
                Experience ex = dataSnapshot.getValue(Experience.class);
                //Toast.makeText(getApplicationContext(),ex.getCompanyName(),Toast.LENGTH_LONG).show();
                experienceList.add(ex);
                wAdapter = new WorkExperienceAdapter(experienceList,StudentDetails.this);
                recExperience.setAdapter(wAdapter);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), StudentActivity.class);
        startActivity(myIntent);
        return true;
    }
}
