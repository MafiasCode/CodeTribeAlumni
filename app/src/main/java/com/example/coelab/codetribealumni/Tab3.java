package com.example.coelab.codetribealumni;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.adapter.ProjectAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Laser on 11/13/2017.
 */
public class Tab3 extends Fragment implements View.OnClickListener{
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private EditText proName,proLink;
    String name,link;
    private ListView proList;
    ProjectAdapter adapter;
    String id;
    private Button addPro;
    FloatingActionButton fab;
    private ArrayList<Project> pros = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_projects,container,false);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        id = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference("Projects");
        fab = (FloatingActionButton) view.findViewById(R.id.btnAddProjects);
        proList = (ListView) view.findViewById(R.id.listOfProjects);
        /*proName = (EditText) view.findViewById(R.id.txtProjectName);
        proLink = (EditText) view.findViewById(R.id.txtProjectLink);*/
        //addPro = (Button) view.findViewById(R.id.btnAddProject);
        fab.setOnClickListener(this);
        proList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Project p = pros.get(position);
                //Toast.makeText(getContext(),p.getProjectName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(p.getProjectLink()));

                startActivity(intent);
            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pros = new ArrayList<>();
                for ( DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //if ( snapshot.getValue() != null) {
                        Project project = snapshot.getValue(Project.class);
                        if (project != null && project.getId().equalsIgnoreCase(id)) {
                            pros.add(project);
                        }

                    //}
                }
                adapter = new ProjectAdapter(getContext(), pros);
                proList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
    @Override
    public void onClick(View v) {
        if(v == fab){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_layout,null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setView(view);
            dialog.setTitle("Add project");
            final EditText appName = (EditText) view.findViewById(R.id.dialogAppName);
            final EditText appLink = (EditText) view.findViewById(R.id.dialogAppLink);
            dialog.setCancelable(true).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String name = appName.getText().toString().trim();
                    String link = appLink.getText().toString().trim();
                    String ids = ref.push().getKey();
                    Project p = new Project(id,name,link);
                    ref.child(ids).setValue(p);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            Dialog d = dialog.create();
            d.show();;
        }
    }
}
