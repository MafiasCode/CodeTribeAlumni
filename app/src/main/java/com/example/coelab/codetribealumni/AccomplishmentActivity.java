package com.example.coelab.codetribealumni;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class AccomplishmentActivity extends AppCompatActivity {
    private ListView accList;
    private FloatingActionButton addAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomplishment);
        setTitle("Accomplishments");
        addAcc = (FloatingActionButton) findViewById(R.id.btnAddAccomplishments);
        accList = (ListView) findViewById(R.id.listOfAcc);
        addAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                dialog.setTitle("Add accomplishment");
                dialog.show();
            }
        });
    }
}
