package com.example.coelab.codetribealumni.pojo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.coelab.codetribealumni.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Laser on 11/14/2017.
 */

public class ProjectAdapter extends ArrayAdapter<Project> {
    public ProjectAdapter(@NonNull Context context, @NonNull List<Project> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.project_layout,parent,false);
        }
        Project p = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.viewProName);
        name.setText("Name: " + p.getProjectName());
        TextView link = (TextView) view.findViewById(R.id.viewProLink);
        link.setText("Link: " + p.getProjectLink());
        return view;
    }
}
