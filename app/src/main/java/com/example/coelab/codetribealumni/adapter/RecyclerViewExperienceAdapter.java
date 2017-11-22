package com.example.coelab.codetribealumni.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coelab.codetribealumni.R;
import com.example.coelab.codetribealumni.pojo.Experience;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codetribe on 11/22/2017.
 */

public class RecyclerViewExperienceAdapter extends   RecyclerView.Adapter<RecyclerViewExperienceAdapter.ViewHolder> {

    List<Experience> experiences;

    public RecyclerViewExperienceAdapter(List<Experience> experiences) {
        this.experiences = experiences;
    }

    @Override
    public RecyclerViewExperienceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_layout, parent, false);
        return new RecyclerViewExperienceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewExperienceAdapter.ViewHolder holder, int position) {

        Experience experience = experiences.get(position);
        holder.txtCompanyname.setText(experience.getCompanyName());
        holder.txtPosition.setText(experience.getPosition());
        holder.txtStartdate.setText(experience.getStartSate());
        holder.txtEnddate.setText(experience.getEndDate());
    }

    @Override
    public int getItemCount() {
        return experiences.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtCompanyname) TextView txtCompanyname;
        @BindView(R.id.txtPosition) TextView txtPosition;
        @BindView(R.id.txtStartdate) TextView txtStartdate;
        @BindView(R.id.txtEnddate) TextView txtEnddate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
