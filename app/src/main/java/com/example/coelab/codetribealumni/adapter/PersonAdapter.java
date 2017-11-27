package com.example.coelab.codetribealumni.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coelab.codetribealumni.Person;
import com.example.coelab.codetribealumni.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Codetribe on 11/16/2017.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements Filterable {

    private ArrayList<Person> mArrayList;
    private ArrayList<Person> mFilteredList;

    public PersonAdapter(ArrayList<Person> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.name.setText(mFilteredList.get(position).getName());
        holder.gender.setText(mFilteredList.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Person> filteredList = new ArrayList<>();

                    for (Person person : mArrayList) {

                        if (person.getName().toLowerCase().contains(charString) || person.getLocation().toLowerCase().contains(charString) ) {

                            filteredList.add(person);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Person>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.studentName) TextView name;
        @BindView(R.id.studGender) TextView gender;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
