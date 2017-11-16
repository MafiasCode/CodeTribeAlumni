package com.example.coelab.codetribealumni;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class FacilitatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    DrawerLayout drawer;

    //===============Firebase Database
    private FirebaseDatabase mdatabase;
    private DatabaseReference myRef,databaseref;
    private String uid;
    private ListView listview;
    private ArrayList<Person> studentList;
    //===============================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilitator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setting up the username and email of the user enetered to the navigation headers
        View fView = navigationView.getHeaderView(0);
        final TextView nav_name = fView.findViewById(R.id.txtUsername);
        final TextView nav_email = fView.findViewById(R.id.txtUseremail);

        //Initializing firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mdatabase = FirebaseDatabase.getInstance();

        listview = (ListView) findViewById(R.id.student_list);
        studentList = new ArrayList<>();
        uid = mFirebaseUser.getUid();
        myRef = mdatabase.getReference().child("Userprofiles").child(uid);
        databaseref = mdatabase.getReference().child("Userprofiles");

       databaseref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Person p = dataSnapshot.getValue(Person.class);
                if(p.getRole().equals("Student"))
                {
                    studentList.add(p);
                }

                PersonAdapter adapter = new PersonAdapter(getBaseContext(),studentList);
                listview.setAdapter(adapter);

                //Trying to put a listener to a list view
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
                    {
                        uid = mFirebaseUser.getUid();

                        Person person = studentList.get(position);
                        Intent intent = new Intent(getBaseContext(),ViewStudentProfileActivity.class);
                        intent.putExtra("uid",uid);
                        intent.putExtra("person",person);
                        startActivity(intent);
                    }
                });

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Person personObj = dataSnapshot.getValue(Person.class);

                if(personObj.getRole().equals("Student"))
                {
                    studentList.add(personObj);
                }

                PersonAdapter adapter = new PersonAdapter(getBaseContext(),studentList);
                listview.setAdapter(adapter);

                //Trying to put a listener to a list view
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
                    {
                        uid = mFirebaseUser.getUid();

                        Person person = studentList.get(position);
                        Intent intent = new Intent(getBaseContext(),ViewStudentProfileActivity.class);
                        intent.putExtra("uid",uid);
                        startActivity(intent);
                    }
                });

                if(personObj != null)
                {
                    String username = personObj.getName() + " " + personObj.getSurname();
                    nav_name.setText(username);
                    nav_email.setText(mFirebaseUser.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.facilitator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id)
    {
        Fragment fragment = null;


        switch(id)
        {
            case R.id.nav_profile:
                Intent intent = new Intent(getBaseContext(),ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_view:
                startActivity(new Intent(getBaseContext(),FacilitatorActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                mFirebaseAuth.signOut();
                startActivity(new Intent(getBaseContext(),SignInActivity.class));
                finish();
                break;
            case R.id.nav_aboutus:
                fragment = new AboutusActivity();
                break;
        }

        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_facilitator,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    //Inner class for adapter
    private class PersonAdapter extends ArrayAdapter<Person>
    {
        public PersonAdapter(@NonNull Context context, @NonNull List<Person> objects) {
            super(context, 0,objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View view = convertView;

            if(view == null)
            {
                view = LayoutInflater.from(getContext()).inflate(R.layout.recycler_items,parent,false);

            }

            Person p = getItem(position);
            TextView name = view.findViewById(R.id.studentName);
            TextView location = view.findViewById(R.id.studLocation);

            name.setText(p.getName() + " " + p.getSurname());
            location.setText(p.getLocation());
            return view;
        }
    }

}
