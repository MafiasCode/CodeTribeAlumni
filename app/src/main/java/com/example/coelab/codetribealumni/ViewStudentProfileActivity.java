package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewStudentProfileActivity extends AppCompatActivity
{
    private EditText name,surname,gender,email,contacts;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase mdatabase;
    private DatabaseReference myRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_profile);

        name = findViewById(R.id.txtName);
        surname = findViewById(R.id.txtSurname);
        gender = findViewById(R.id.txtGender);
        email = findViewById(R.id.txtEmail);
        contacts = findViewById(R.id.txtContactdetails);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        Person p =(Person) intent.getSerializableExtra("person");

        name.setText(p.getName());
        surname.setText(p.getSurname());
        gender.setText(p.getGender());
        contacts.setText(p.getCell());
        email.setText(p.getEmail());
    }
}
