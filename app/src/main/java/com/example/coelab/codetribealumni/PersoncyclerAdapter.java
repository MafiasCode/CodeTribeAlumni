package com.example.coelab.codetribealumni;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Codetribe on 11/10/2017.
 */

public class PersoncyclerAdapter extends RecyclerView.Adapter<PersoncyclerAdapter.ViewHolder>
{
    private ArrayList<PersonCycler> list;
    private Context mContext;

    public PersoncyclerAdapter(Context mContext,ArrayList<PersonCycler> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        //final PersonCycler p = list.get(position);
        holder.txtName.setText(list.get(position).getName());
        holder.txtSurname.setText(list.get(position).getSurname());
        holder.img_profile.setImageResource(list.get(position).getImg_id());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtName,txtSurname;
        private CircleImageView img_profile;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView)itemView.findViewById(R.id.txtUsername);
            txtSurname = (TextView) itemView.findViewById(R.id.userSurname);
            //img_profile = (CircleImageView) itemView.findViewById(R.id.image_view);
        }


    }
}
