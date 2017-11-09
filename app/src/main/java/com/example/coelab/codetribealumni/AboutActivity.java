package com.example.coelab.codetribealumni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private TextView txtName,txtDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("About Us");

        txtName = (TextView) findViewById(R.id.txt_name);
        txtDesc = (TextView) findViewById(R.id.txt_desc);

        txtName.setText("CodeTribe Alumni");
        txtDesc.setText("CodeTibe Alumni is a mobile app for Codetribe Academy");
    }


    public void title1(View view){

        txtName.setText("Yanga Yongama Tsele");
        txtDesc.setText("Yanga is From Eastern Cape");
    }

    public void title2(View view){

        txtName.setText("Pontsho Richard Modisane");
        txtDesc.setText("Pontsho is from Siyabusa");
    }

    public void title3(View view){

        txtName.setText("Sarah Mahlangu ");
        txtDesc.setText("Sarah is Sarakie");
    }

    public void title4(View view){

        txtName.setText("Tharollo Moraba");
        txtDesc.setText("Tharollo is our transport guy");
    }
}
