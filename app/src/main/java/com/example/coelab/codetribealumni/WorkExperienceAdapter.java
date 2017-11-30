package com.example.coelab.codetribealumni;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.pojo.Experience;

import java.util.ArrayList;

/**
 * Created by Laser on 11/28/2017.
 */

public class WorkExperienceAdapter extends RecyclerView.Adapter<WorkExperienceAdapter.ExperienceHolder>{
    private ArrayList<Experience> exList;
    private Activity context;

    public WorkExperienceAdapter(ArrayList<Experience> experienceList, Activity activity) {
        exList = experienceList;
        context = activity;
    }

    @Override
    public ExperienceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        return new ExperienceHolder(view);
    }

    @Override
    public void onBindViewHolder(ExperienceHolder holder, int position) {
        Experience experience = exList.get(position);
        holder.setComName(experience.getCompanyName());
        holder.setComPosition(experience.getPosition());
        holder.setComStart(experience.getStartSate());
        holder.setComEnd(experience.getEndDate());
    }

    @Override
    public int getItemCount() {
        if(exList == null){
            return 0;
        }
        return exList.size();
    }

    class ExperienceHolder extends RecyclerView.ViewHolder {
        TextView comName,comPosition,comStart,comEnd;
        public ExperienceHolder(View itemView) {
            super(itemView);
            comName = itemView.findViewById(R.id.viewExCompany);
            comPosition = itemView.findViewById(R.id.viewExPosition);
            comStart = itemView.findViewById(R.id.viewExStart);
            comEnd = itemView.findViewById(R.id.viewExEnd);
        }
        public void setComName(String name){
            comName.setText(name);
        }
        public void setComPosition(String position) {
            comPosition.setText(position);
        }
        public void setComStart(String start) {
            comStart.setText(start);
        }
        public void setComEnd(String end) {
            comEnd.setText(end);
        }
    }
}
