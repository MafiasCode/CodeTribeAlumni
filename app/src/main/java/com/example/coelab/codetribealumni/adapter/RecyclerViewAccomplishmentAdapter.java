package com.example.coelab.codetribealumni.adapter;

/**
 * Created by Yanga on 11/29/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coelab.codetribealumni.R;
import com.example.coelab.codetribealumni.pojo.Accomplishments;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class RecyclerViewAccomplishmentAdapter extends RecyclerView.Adapter<RecyclerViewAccomplishmentAdapter.ViewHolder>{

    List <Accomplishments> accomplishments;

    public RecyclerViewAccomplishmentAdapter(List<Accomplishments> accomplishments){
        this.accomplishments = accomplishments;

    }

    @Override
    public RecyclerViewAccomplishmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accomplishment_layout,parent,false);
        return new RecyclerViewAccomplishmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAccomplishmentAdapter.ViewHolder holder,int position){
        Accomplishments accomplish = accomplishments.get(position);
        holder.txtcourseName.setText(accomplish.getcourseName());
        holder.txtqualification.setText(accomplish.getqualification());
        holder.txtinstitution.setText(accomplish.getinstitution());
        holder.txtyear.setText(accomplish.getyear());

    }

    @Override
    public int getItemCount(){
        return accomplishments.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.txtCourseName) TextView txtcourseName;
        @BindView(R.id.txtQualification) TextView txtqualification;
        @BindView(R.id.txtInstitution) TextView txtinstitution;
        @BindView(R.id.txtYear) TextView txtyear;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

