package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewStudentInfo extends AppCompatActivity {
    private TextView names,gender,role,cellphone,email,location,year;
    //private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_info);
        //Adding back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Student information");
        //Person object
        Person p = (Person) getIntent().getSerializableExtra("Person");
        //finding views
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
