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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coelab.codetribealumni.R;
import com.example.coelab.codetribealumni.data.Project;
import com.example.coelab.codetribealumni.pojo.Accomplishments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.List;

/**
 * Created by Yanga on 11/29/2017.
 */

public class AccomplishmentAdapter extends ArrayAdapter<Accomplishments> {

    public AccomplishmentAdapter(@NonNull Context context, @NonNull List<Accomplishments> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.accomplishment_layout,null);
        }
        Accomplishments ex = getItem(position);
        TextView cors = (TextView) view.findViewById(R.id.txtCourseName);
        cors.setText(ex.getcourseName());
        TextView qlf = (TextView) view.findViewById(R.id.txtQualification);
        qlf.setText(ex.getqualification());
        TextView inst = (TextView) view.findViewById(R.id.txtInstitution);
        inst.setText(ex.getinstitution());
        TextView yr = (TextView) view.findViewById(R.id.txtYear);
        yr.setText(ex.getyear());
        return view;
    }

}
