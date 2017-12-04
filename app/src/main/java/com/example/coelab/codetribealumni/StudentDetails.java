package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {
    private TextView nams,gender,role,cellphone,email,location,year;
    private CollapsingToolbarLayout collapsing = null;
    private ListView exList, proList;
    private ProjectsAdapter adapter;
    private WorkExperienceAdapter wAdapter;
    String id;
    ArrayList<Experience> experienceList = new ArrayList<>();
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        //getting intent
        Intent intent = getIntent();
        Person p = (Person) intent.getSerializableExtra("Person");
        id = p.getId();
        viewData("Experience");
        viewData("Projects");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapseActionView);
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
            nams.setText(p.getName() + " " + p.getSurname());
            gender.setText(p.getGender());
            role.setText(p.getRole());
            cellphone.setText(p.getCell());
            email.setText(p.getEmail());
            location.setText(p.getLocation());
            year.setText(p.getYear());
        }
    }
    public void viewData(final String data){
        ref = FirebaseDatabase.getInstance().getReference(data);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final ArrayList<Project> projects = new ArrayList<>();
                final ArrayList<Experience> experience = new ArrayList<>();
                if(data.equalsIgnoreCase("Projects")){
                    Project p = dataSnapshot.getValue(Project.class);
                    if(p != null && p.getId().equalsIgnoreCase(id)){
                        projects.add(p);
                        ProjectAdapter adapter = new ProjectAdapter(getApplicationContext(),projects);
                        proList.setAdapter(adapter);
                    }

                }
                else if(data.equalsIgnoreCase("Experience")){
                    Experience e = dataSnapshot.getValue(Experience.class);
                    if(e != null && e.getId().equalsIgnoreCase(id)){
                        experience.add(e);
                        ExperienceAdapter adapter = new ExperienceAdapter(getApplicationContext(),experience);
                        exList.setAdapter(adapter);
                    }
                }
                else if(data.equalsIgnoreCase("Accomplishments")){
                    // p = dataSnapshot.getValue(Project.class);
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
    }

}
