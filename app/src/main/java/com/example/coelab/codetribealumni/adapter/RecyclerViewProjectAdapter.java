package com.example.coelab.codetribealumni.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coelab.codetribealumni.R;
import com.example.coelab.codetribealumni.data.Project;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codetribe on 11/22/2017.
 */

public class RecyclerViewProjectAdapter extends  RecyclerView.Adapter<RecyclerViewProjectAdapter.ViewHolder> {

    List<Project> project;

    public RecyclerViewProjectAdapter(List<Project> project) {
        this.project = project;
    }

    @Override
    public RecyclerViewProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_layout, parent, false);
        return new RecyclerViewProjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewProjectAdapter.ViewHolder holder, int position) {

        Project projects = project.get(position);
        holder.txtprojectName.setText(projects.getProjectName());
        holder.txtprojectLink.setText(projects.getProjectLink());
    }

    @Override
    public int getItemCount() {
        return project.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtprojectName) TextView txtprojectName;
        @BindView(R.id.txtprojectLink) TextView txtprojectLink;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
