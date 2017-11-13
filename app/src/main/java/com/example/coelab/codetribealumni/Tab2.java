package com.example.coelab.codetribealumni;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laser on 11/13/2017.
 */

public class Tab2 extends Fragment {
    private ListView studentList;
    private DatabaseReference ref;
    ArrayList<Person> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2,container,false);
        studentList = (ListView) view.findViewById(R.id.studList);
        list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Userprofiles");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Person p = dataSnapshot.getValue(Person.class);
                if(p.getRole().equalsIgnoreCase("student")){
                    list.add(p);
                }
                //ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(getContext(),android.R.layout.simple_list_item_1,list);
                StudentAdapter adapter = new StudentAdapter(getContext(),list);
                studentList.setAdapter(adapter);
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
        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    Person p = dataSnapshot.getValue(Person.class);
                    list.add(p);
                    ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(getContext(),android.R.layout.simple_list_item_1,list);
                    studentList.setAdapter(adapter);
                }
                //StudentAdapter adapter = new StudentAdapter(getContext(),list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        return view;
    }
    private class StudentAdapter extends ArrayAdapter<Person>{
        public StudentAdapter(@NonNull Context context, @NonNull List<Person> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.simple_list,parent,false);
            }
            Person p = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.viewName);
            name.setText(p.getName() + " " + p.getSurname());
            TextView gen = (TextView) view.findViewById(R.id.viewGender);
            gen.setText(p.getGender());
            return view;
        }
    }
}
