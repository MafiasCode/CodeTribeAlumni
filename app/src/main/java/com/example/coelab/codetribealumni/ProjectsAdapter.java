package com.example.coelab.codetribealumni;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coelab.codetribealumni.data.Project;

import java.util.ArrayList;

/**
 * Created by Laser on 11/28/2017.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectHolder> {
    private ArrayList<Project> proList;
    private Activity context;

    public ProjectsAdapter(ArrayList<Project> listOfProjects, Activity applicationContext) {
        proList = listOfProjects;
        context = applicationContext;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_layout, parent, false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectHolder holder, int position) {
        Project project = proList.get(position);
        holder.setProjectName(project.getProjectName());
        holder.setProjectLink(project.getProjectLink());
    }

    @Override
    public int getItemCount() {
        if (proList == null) {
            return 0;
        }
        return proList.size();
    }

    class ProjectHolder extends RecyclerView.ViewHolder implements DialogInterface.OnClickListener {
        TextView projectName;
        TextView projectLink;

        public ProjectHolder(View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.viewProName);
            projectLink = itemView.findViewById(R.id.viewProLink);
        }

        public void setProjectName(String projectNam) {
            projectName.setText(projectNam);
        }

        public void setProjectLink(String projectLin) {
            projectLink.setText(projectLin);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    }
}
