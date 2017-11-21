package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coelab.codetribealumni.pojo.Experience;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExperience extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private EditText company,position,startDate,endDate;
    private Button addExperience;
    String myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);
        getSupportActionBar().setTitle("Add experience");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        myId = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference("Experience");
        company = (EditText) findViewById(R.id.exCompany);
        position = (EditText) findViewById(R.id.exPosition);
        startDate = (EditText) findViewById(R.id.exStartDate);
        endDate = (EditText) findViewById(R.id.exEndDate);
        addExperience = (Button) findViewById(R.id.btnExperience);
        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ref.push().getKey();
                String com = company.getText().toString();
                String pos = position.getText().toString();
                String start = startDate.getText().toString();
                String end = endDate.getText().toString();
                if(TextUtils.isEmpty(com)){
                    company.setError("Field cannot be empty!");
                    company.setHintTextColor(Color.RED);
                    return;
                }
                if(TextUtils.isEmpty(pos)){
                    position.setError("Field cannot be empty!");
                    position.setHintTextColor(Color.RED);
                    return;
                }
                if(TextUtils.isEmpty(start)){
                    startDate.setError("Field cannot be empty!");
                    startDate.setHintTextColor(Color.RED);
                    return;
                }
                if(TextUtils.isEmpty(end)){
                    endDate.setError("Field cannot be empty!");
                    endDate.setHintTextColor(Color.RED);
                    return;
                }
                if(start.length() != 10){
                    startDate.setError("Not a valid date!");
                    startDate.setHintTextColor(Color.RED);
                    return;
                }
                if(end.length() > 10){
                    endDate.setError("Not a valid date!");
                    endDate.setHintTextColor(Color.RED);
                    return;
                }
                Experience e = new Experience(myId,com,pos,start,end);
                ref.child(id).setValue(e);
                startActivity(new Intent(getApplicationContext(),WorkExperience.class));
            }
        });
    }
}
