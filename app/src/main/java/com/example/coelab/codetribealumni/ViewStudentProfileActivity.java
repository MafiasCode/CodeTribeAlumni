package com.example.coelab.codetribealumni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.example.coelab.codetribealumni.adapter.RecyclerViewExperienceAdapter;
import com.example.coelab.codetribealumni.adapter.RecyclerViewProjectAdapter;
import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.pojo.Experience;
import com.example.coelab.codetribealumni.utils.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ViewStudentProfileActivity extends AppCompatActivity
{
    public static final String profile_object = "profile_object";
    public static final String profile_uuid = "profile_uuid";
    private EditText name,surname,gender,email,contacts;

    //Firebase
    private DatabaseReference databaseReference;
    private ArrayList<Project> projects_list;
    private ArrayList<Experience> experience_list;
    private RecyclerViewExperienceAdapter experinceAdapter;
    private RecyclerViewProjectAdapter projectAdapter;
    //private ProjectsAdapter adapterprojects; just now now
    Person person;
    RecyclerView.LayoutManager layoutManager;

    //Declare for the project listview
    private TextView txtProjectname,edLink;
    RecyclerView worklist,project_listview;
    //private ListView project_listview; just now now
    String uuid;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_profile);
        context = getBaseContext();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Making the image view to be round
        ImageView profileImage = findViewById(R.id.profilepic);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.blank_image1);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        profileImage.setImageDrawable(roundedBitmapDrawable);

        //Finding the fields
        name = findViewById(R.id.txtName);
        surname = findViewById(R.id.txtSurname);
        gender = findViewById(R.id.txtGender);
        email = findViewById(R.id.txtEmail);
        contacts = findViewById(R.id.txtContactdetails);

        //The work experience list view fields
        worklist = findViewById(R.id.work_list);
        layoutManager = new LinearLayoutManager(this);
        worklist.setLayoutManager(layoutManager);
        worklist.setHasFixedSize(true);

        //Finding the recyclerview for the project
        project_listview = findViewById(R.id.project_list);
        layoutManager = new LinearLayoutManager(this);
        project_listview.setLayoutManager(layoutManager);
        project_listview.setHasFixedSize(true);
        project_listview.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, float x, float y)
            {
                Project project = projects_list.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(project.getProjectLink()));
                startActivity(intent);
            }
        }));

        txtProjectname = findViewById(R.id.txtprojectName);
        edLink = findViewById(R.id.txtprojectLink);

       //Getting personal info
        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra(profile_object);
        uuid = intent.getStringExtra(profile_uuid);
        Log.i("Sarah ","User ID : "+person.getId());
        if (person != null)
        {
            //uuid = person.getId();
            name.setText(person.getName());
            surname.setText(person.getSurname());
            gender.setText(person.getGender());
            contacts.setText(person.getCell());
            email.setText(person.getEmail());
        }


        //Wxperience database actions
        //experienceReference = databaseReference.child("Experience").child(uuid);
        Query experienceQuery = databaseReference.child("Experience").orderByChild("id").equalTo(uuid);
        experienceQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                experience_list = new ArrayList<>();
                Log.i(" OMG", dataSnapshot.toString());
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.getValue() != null) {
                        Experience experience = snapshot.getValue(Experience.class);
                        Log.i("mendie", snapshot.toString());
                        experience_list.add(experience);
                    }
                }

                experinceAdapter = new RecyclerViewExperienceAdapter(experience_list);
                worklist.setAdapter(experinceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Project database actions
        Query projectQuery = databaseReference.child("Projects").orderByChild("id").equalTo(uuid);
        projectQuery.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Like Really", dataSnapshot.toString());
                projects_list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.getValue() != null) {
                        Project projects = snapshot.getValue(Project.class);
                            Log.i("Sarah", snapshot.toString());
                             projects_list.add(projects);
                        }
                }

                projectAdapter = new RecyclerViewProjectAdapter(projects_list);
                project_listview.setAdapter(projectAdapter);

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class ProjectsAdapter extends ArrayAdapter<Project>
    {
        public ProjectsAdapter(@NonNull Context context, @NonNull List<Project> objects) {
            super(context, 0,objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View view = convertView;

            if(view == null)
            {
                view = LayoutInflater.from(getContext()).inflate(R.layout.student_projects,parent,false);
            }

            Project p = getItem(position);
            TextView projectName = view.findViewById(R.id.txtprojectName);
            TextView projectLink = view.findViewById(R.id.txtprojectLink);

            projectName.setText(p.getProjectName());
            projectLink.setText(p.getProjectLink());
            return view;
        }
    }

    /*Adapter experience adapter
    private class ExperinceAdapter extends ArrayAdapter<Experience>
    {
        public ExperinceAdapter(@NonNull Context context, @NonNull List<Experience> objects) {
            super(context, 0,objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View view = convertView;

            if(view == null)
            {
                view = LayoutInflater.from(getContext()).inflate(R.layout.experience_layout,parent,false);
            }

            Experience experience = getItem(position);
            TextView companyname = view.findViewById(R.id.txtCompanyname);
            TextView jobTitle = view.findViewById(R.id.txtPosition);
            TextView startdate = view.findViewById(R.id.txtStartdate);
            TextView enddate = view.findViewById(R.id.txtEnddate);

            companyname.setText(experience.getCompanyName());
            jobTitle.setText(experience.getPosition());
            startdate.setText(experience.getStartSate());
            enddate.setText(experience.getEndDate());
            return view;
        }
    }*/


}





