package com.example.coelab.codetribealumni;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class Tab1 extends Fragment {

    EditText edt_name;
    EditText edt_surname;
    EditText edt_mobile;
    EditText edt_email;
    EditText edt_role;
    EditText edt_year;
    Spinner spn_location;
    Spinner spn_gender;
    Button btn_updateProfile;

    String name,surname,contact,email,password,gender,location,year,role;
    private String uid;
    private Person person;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseUser firebaseUser;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        //Initialize the view
        /*
        edt_name = (EditText) view.findViewById(R.id.txt_name);
        edt_surname = (EditText) view.findViewById(R.id.txt_surname);
        edt_mobile = (EditText) view.findViewById(R.id.txt_mobileno);
        edt_email = (EditText)view.findViewById(R.id.txt_email);
        edt_role = (EditText)view.findViewById(R.id.txt_role);
        edt_year = (EditText)view.findViewById(R.id.txt_year);
        spn_gender = (Spinner)view.findViewById(R.id.spn_gender);
        spn_location = (Spinner)view.findViewById(R.id.spn_location);


        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uid = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference().child("Userprofiles").child(uid);


        // for spinner
        final ArrayAdapter<CharSequence> genderAdapter;
        final ArrayAdapter<CharSequence> locationAdapter;

        locationAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.location, android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spn_location.setAdapter(locationAdapter);

        genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_gender.setAdapter(genderAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Person persons = dataSnapshot.getValue(Person.class);

                if (persons != null) {

                    Toast.makeText(getActivity(), "person is not null", Toast.LENGTH_SHORT).show();
                    edt_name.setText(persons.getName());
                    edt_surname.setText(persons.getSurname());
                    edt_mobile.setText(persons.getCell());
                    edt_email.setText(persons.getEmail());
                    edt_role.setText(persons.getRole());
                    edt_year.setText(persons.getYear());

                    for (int g = 0; g < spn_gender.getCount(); g++){
                        if (spn_gender.getItemAtPosition(g).toString().equals(persons.getGender())) {
                            spn_gender.setSelection(g);
                            break;
                        }
                    }

                    for (int l = 0; l < spn_location.getCount(); l++ ){
                        if (spn_location.getItemAtPosition(l).toString().equals(persons.getLocation())){
                            spn_location.setSelection(l);
                            break;
                        }
                    }
                    //spn_gender.getSelectedItem(persons.getGender());
                }
                else {
                    Toast.makeText(getActivity(), "Sorry there seem to be no information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        name = edt_name.getText().toString();*/


        //updateUserProfile();

        return view;
    }

    private void updateUserProfile(String name, String surname, String mobile ){

        databaseReference.child("name").setValue("tharoll");

    }
}
