package com.example.coelab.codetribealumni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coelab.codetribealumni.adapter.PersonAdapter;
import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.example.coelab.codetribealumni.data.Project;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentProfileActivity extends AppCompatActivity
{
    public static final String profile_object = "profile_object";
    private EditText name,surname,gender,email,contacts;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase mdatabase;
    private DatabaseReference myRef;
    private String uid;

    private ArrayList<Project> projects_list;

    //Declare for the project listview
    private TextView txtProjectname;
    private  EditText edLink;

    //Adapter
    private ProjectAdapter adater;
    private ListView project_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_profile);

        //Making the image view to be round
        ImageView profileImage = findViewById(R.id.profilepic);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.blank_image1);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        profileImage.setImageDrawable(roundedBitmapDrawable);


        name = findViewById(R.id.txtName);
        surname = findViewById(R.id.txtSurname);
        gender = findViewById(R.id.txtGender);
        email = findViewById(R.id.txtEmail);
        contacts = findViewById(R.id.txtContactdetails);

        //Assigning  the arraylist
        projects_list = new ArrayList<>();

        //Initialize the listview
        project_listview = findViewById(R.id.profile_list);

        //Initializing the student projects fields
        txtProjectname = findViewById(R.id.txtprojectName);
        edLink = findViewById(R.id.edprojectLink);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        Intent intent = getIntent();
        Person p = (Person) intent.getSerializableExtra(profile_object);

        if (p != null) {
            name.setText(p.getName());
            surname.setText(p.getSurname());
            gender.setText(p.getGender());
            contacts.setText(p.getCell());
            email.setText(p.getEmail());
        }



    }
}




