package com.example.coelab.codetribealumni.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.R;
import com.example.coelab.codetribealumni.data.Project;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Laser on 11/14/2017.
 */

public class ProjectAdapter extends ArrayAdapter<Project> {
    Project p;
    DatabaseReference ref;

    public ProjectAdapter(@NonNull Context context, @NonNull List<Project> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.project_layout, parent, false);
        }
        p = getItem(position);
        ref = FirebaseDatabase.getInstance().getReference("Projects");
        TextView name = (TextView) view.findViewById(R.id.viewProName);
        name.setText(p.getProjectName());
        TextView link = (TextView) view.findViewById(R.id.viewProLink);
        link.setText(p.getProjectLink());
        ImageView edit = (ImageView) view.findViewById(R.id.imgEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_layout, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setView(view);
                dialog.setTitle("Edit project");
                final EditText appName = (EditText) view.findViewById(R.id.dialogAppName);
                final EditText appLink = (EditText) view.findViewById(R.id.dialogAppLink);
                appName.setText(p.getProjectName());
                appLink.setText(p.getProjectLink());
                dialog.setCancelable(true).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = appName.getText().toString().trim();
                        String link = appLink.getText().toString().trim();
                        String ids = ref.push().getKey();
                        String id = p.getId();
                        Project p = new Project(id, name, link);
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
                d.show();
            }
        });
        return view;
    }
}
