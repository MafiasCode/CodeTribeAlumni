package com.example.coelab.codetribealumni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Codetribe on 11/10/2017.
 */

public class CodetribersProfiles extends Fragment
{
    private FirebaseDatabase mdatabase;
    private DatabaseReference myRef;
    private ChildEventListener mChildEvenListener;

    private ArrayList<PersonCycler> personList;
    private RecyclerView recyclerView;
    private PersoncyclerAdapter personAdapter;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //Initializing firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //viewcodetribers();

        //testing=========================
        mdatabase = FirebaseDatabase.getInstance();
        myRef = mdatabase.getReference().child("Userprofiles");
        recyclerView = (RecyclerView)inflater.inflate(R.layout.recycler_items,container,false);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        personList = new ArrayList<>();

        mChildEvenListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                PersonCycler p = dataSnapshot.getValue(PersonCycler.class);
                personList.add(p);

                //Testing
                p.setImg_id(R.drawable.pic1);
                personAdapter = new PersoncyclerAdapter(recyclerView.getContext(),personList);
                recyclerView.setAdapter(personAdapter);

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
        };

        myRef.addChildEventListener(mChildEvenListener);

        return  recyclerView;

    }
}
