package com.example.coelab.codetribealumni;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.adapter.PersonAdapter;
import com.example.coelab.codetribealumni.utils.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacilitatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //@BindView(R.id.student_list) ListView listview;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.facilitators) RecyclerView mRecyclerView;
    TextView nav_name,nav_email;

    //Firebase instance variables
    private FirebaseUser mFirebaseUser;

    //Firebase Database
    private FirebaseDatabase mdatabase;
    private DatabaseReference myRef,databaseref,databasereference;
    private Button totalStudents;

    private String uid;
    private ArrayList<Person> studentList;
    private ArrayList<String> studentListIds;
    private PersonAdapter adapter;
    Context context;
    String location;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilitator);
        ButterKnife.bind(this);
        context = getBaseContext();
        setSupportActionBar(toolbar);

        totalStudents = findViewById(R.id.count);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Setting up the username and email of the user enetered to the navigation headers
        nav_name = navigationView.getHeaderView(0).findViewById(R.id.txtUsername);
        nav_email = navigationView.getHeaderView(0).findViewById(R.id.txtUseremail);

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, float x, float y) {
                Person person = studentList.get(position);
                String uuid = studentListIds.get(position);
                Intent intent = new Intent(context, ViewStudentProfileActivity.class);
                intent.putExtra(ViewStudentProfileActivity.profile_object, person);
                intent.putExtra(ViewStudentProfileActivity.profile_uuid, uuid);
                startActivity(intent);
            }
        }));


        //Initializing firebase
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mdatabase = FirebaseDatabase.getInstance();
        uid = mFirebaseUser.getUid();

        myRef = mdatabase.getReference().child("Userprofiles").child(uid);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {

        } else {
            // Navigate to MainActivity
            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
        }

        databaseref = mdatabase.getReference().child("Userprofiles");
        databaseref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList = new ArrayList<>();
                studentListIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue() != null) {
                        Person person = snapshot.getValue(Person.class);
                        //Added now now for test
                        if ("Student".equals(person.getRole())) {
                            Log.i("Sarah", snapshot.toString());
                            studentList.add(person);
                            studentListIds.add(snapshot.getKey());
                            counter ++;

                        }
                    }


                }


                //totalStudents.setText(counter);
                adapter = new PersonAdapter(studentList);
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);
                totalStudents.setText(String.valueOf(counter));
                //Toast.makeText(getApplicationContext(), " " + counter ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if (dataSnapshot.getValue() != null) {
                    Person personObj = dataSnapshot.getValue(Person.class);
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

        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchMenu.getActionView();
        search(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(),SignInActivity.class));
                finish();
                break;
            case R.id.nav_aboutus:
                intent = new Intent(getBaseContext(),About_Us_Activity.class);
                startActivity(intent);
        }

        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_facilitator,fragment);
            ft.commit();
        }

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

}
