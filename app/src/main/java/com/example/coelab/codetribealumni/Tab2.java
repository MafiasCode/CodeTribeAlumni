package com.example.coelab.codetribealumni;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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

/**
 * Created by Laser on 11/13/2017.
 */

public class Tab2 extends Fragment {
    private ListView studentList;
    private DatabaseReference ref;
    FirebaseAuth auth;
    ArrayList<Person> list = new ArrayList<>();
    String id,location;
    StudentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2,container,false);
        studentList = (ListView) view.findViewById(R.id.studList);
        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person p = list.get(i);
                //Toast.makeText(getContext(), p.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),ViewStudentInfo.class);
                intent.putExtra("Person",p);
                startActivity(intent);
            }
        });
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        id = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference("Userprofiles").child(id);
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Person p = dataSnapshot.getValue(Person.class);
                if(p != null){
                    location = p.getLocation();
                    if(location != null)
					{
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Userprofiles");
                        ref2.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Person person = dataSnapshot.getValue(Person.class);
                                if(person.getRole().equalsIgnoreCase("student") && person.getLocation().equalsIgnoreCase(location) && person != null){
                                    list.add(person);
                                }
                                adapter = new StudentAdapter(getContext(),list);
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
                        
                    }
                    else{
                        Toast.makeText(getContext(), "Location not specified!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            TextView loc = (TextView) view.findViewById(R.id.viewGender);
            loc.setText(p.getLocation());
            return view;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }
}
