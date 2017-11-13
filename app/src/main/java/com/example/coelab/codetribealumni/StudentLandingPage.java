package com.example.coelab.codetribealumni;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentLandingPage extends Activity {
    private Spinner edYear;
    private Button search;
    ListView list;
    private DatabaseReference ref;
    ArrayList<Person> studs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = FirebaseDatabase.getInstance().getReference().child("Userprofiles");
        setContentView(R.layout.content_student_landing_page);
        edYear = (Spinner) findViewById(R.id.searchByYear);
        search = (Button)findViewById(R.id.btnSearchTribers);
        list = (ListView) findViewById(R.id.listCodeTribers);
        final String[] lists = {"2018","2017","2016","2015","2014"};
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(StudentLandingPage.this, android.R.layout.simple_dropdown_item_1line, lists);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edYear.setAdapter(yearsAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Person p = dataSnapshot.getValue(Person.class);
                //Toast.makeText(getApplicationContext(),p.getGender(),Toast.LENGTH_LONG).show();
                if(p.getRole().equalsIgnoreCase("student")){
                    studs.add(p);
                }
                //ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(getApplicationContext(),android.R.layout.simple_list_item_1,studs);
                StudentAdapter adapter = new StudentAdapter(getApplicationContext(),studs);
                list.setAdapter(adapter);
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