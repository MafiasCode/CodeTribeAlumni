package com.example.coelab.codetribealumni;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
/**
 * Created by Laser on 11/13/2017.
 */

public class Tab1 extends Fragment implements View.OnClickListener {
    private EditText name, surname, cellphone, email, txtGender, txtRole, txtLocation, txtYear;
    private AppCompatSpinner gender, year, role, location;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private FloatingActionButton edit;
    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        //find views
        edit = (FloatingActionButton) view.findViewById(R.id.btnEditPros);
        edit.setOnClickListener(this);
        txtGender = (EditText) view.findViewById(R.id.txt_profile_gender);
        txtRole = (EditText) view.findViewById(R.id.txt_profile_role);
        txtLocation = (EditText) view.findViewById(R.id.txt_profile_location);
        txtYear = (EditText) view.findViewById(R.id.txt_profile_year);
        name = (EditText) view.findViewById(R.id.txt_profile_name);
        surname = (EditText) view.findViewById(R.id.txt_profile_surname);
        cellphone = (EditText) view.findViewById(R.id.txt_profile_phoneNo);
        email = (EditText) view.findViewById(R.id.txt_profile_email);
        gender = view.findViewById(R.id.genderSpinner);
        year = view.findViewById(R.id.yearSpinner);
        role = view.findViewById(R.id.roleSpinner);
        location = view.findViewById(R.id.locationSpinner);
        //Database connection
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("Userprofiles").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Person p = dataSnapshot.getValue(Person.class);
                if (p != null) {
                    name.setText(p.getName());
                    surname.setText(p.getSurname());
                    cellphone.setText(p.getCell());
                    email.setText(p.getEmail());
                    txtGender.setText(p.getGender());
                    txtLocation.setText(p.getLocation());
                    txtRole.setText(p.getRole());
                    txtYear.setText(p.getYear());
                    gender.setVisibility(View.GONE);
                    role.setVisibility(View.GONE);
                    year.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == edit) {
            //Toast.makeText(getContext(), "Editable", Toast.LENGTH_SHORT).show();
            name.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            surname.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            cellphone.setInputType(InputType.TYPE_CLASS_PHONE);
            email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            txtGender.setVisibility(View.GONE);
            txtYear.setVisibility(View.GONE);
            txtLocation.setVisibility(View.GONE);
            txtRole.setVisibility(View.GONE);
            gender.setVisibility(View.VISIBLE);
            role.setVisibility(View.VISIBLE);
            year.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            String[] genders = {"Choose gender", "Male", "Female"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, genders);
            gender.setAdapter(adapter1);
            String[] roles = {"Choose role", "Facilitator", "Student"};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roles);
            role.setAdapter(adapter2);
            String[] years = {"Choose year", "2017", "2018", "2019", "2020", "2021", "2022"};
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
            year.setAdapter(adapter3);
            String[] locations = {"Choose location", "Tshwane", "Alexandra", "Soweto", "Tembisa"};
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, locations);
            location.setAdapter(adapter4);
        }

    }
}
