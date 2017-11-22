package com.example.coelab.codetribealumni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class About_Us_Activity extends AppCompatActivity {

    private TextView txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us_);
        setTitle("About Us");

        txtDesc = (TextView) findViewById(R.id.txt_desc);



        txtDesc.setText("The app will feature curated capstone projects from CodeTribe alumni including apps on the Google Play Store. " +
                "It will also display alumni accomplishments and profiles, career opportunities");

    }
}
