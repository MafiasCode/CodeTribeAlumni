package com.example.coelab.codetribealumni;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.utils.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by Laser on 11/28/2017.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectHolder> {
    private ArrayList<Project> proList;
    private Activity context;
    Project project;
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
        project = proList.get(position);
        //testing
        holder.setProjectName(project.getProjectName());
        holder.setProjectLink(project.getProjectLink());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = project.getProjectLink();
                if(link != null){
                    Toast.makeText(context, link, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com")));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (proList == null) {
            return 0;
        }
        return proList.size();
    }

    class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView projectName;
        TextView projectLink;
        LinearLayout layout;

        public ProjectHolder(View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.viewProName);
            projectLink = itemView.findViewById(R.id.viewProLink);
            layout = (LinearLayout) itemView.findViewById(R.id.proLayout);
        }

        public void setProjectName(String projectNam) {
            projectName.setText(projectNam);
        }

        public void setProjectLink(String projectLin) {
            projectLink.setText(projectLin);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
