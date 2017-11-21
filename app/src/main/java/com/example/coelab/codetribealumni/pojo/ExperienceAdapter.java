package com.example.coelab.codetribealumni.pojo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coelab.codetribealumni.R;

import java.util.List;

/**
 * Created by Laser on 11/17/2017.
 */

public class ExperienceAdapter extends ArrayAdapter<Experience >{
    public ExperienceAdapter(@NonNull Context context, @NonNull List<Experience> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout,null);
        }
        Experience ex = getItem(position);
        TextView com = (TextView) view.findViewById(R.id.viewExCompany);
        com.setText(ex.getCompanyName());
        TextView pos = (TextView) view.findViewById(R.id.viewExPosition);
        pos.setText(ex.getPosition());
        TextView start = (TextView) view.findViewById(R.id.viewExStart);
        start.setText(ex.getStartSate());
        TextView end = (TextView) view.findViewById(R.id.viewExEnd);
        end.setText(ex.getEndDate());
        return view;
    }

}
