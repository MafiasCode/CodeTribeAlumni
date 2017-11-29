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

public class AddAccomplishment extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private EditText course,qualification,institution,year;
    private Button addaccomplishment;
    String myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accomplishment);
        getSupportActionBar().setTitle("Add Accomplishment");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        myId = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference("Accomplishments");
        course = (EditText) findViewById(R.id.acc_course);
        qualification = (EditText) findViewById(R.id.acc_qualification);
        institution = (EditText) findViewById(R.id.acc_institution);
        year = (EditText) findViewById(R.id.acc_year);
        addaccomplishment = (Button) findViewById(R.id.btn_AddAccomplishment);

        addaccomplishment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ref.push().getKey();
                String crs = course.getText().toString();
                String qlf = qualification.getText().toString();
                String inst = institution.getText().toString();
                String yr = year.getText().toString();
                if(TextUtils.isEmpty(crs)){
                    course.setError("Field cannot be empty!");
                    course.setHintTextColor(Color.RED);
                    return;
                }
                if(TextUtils.isEmpty(qlf)){
                    qualification.setError("Field cannot be empty!");
                    qualification.setHintTextColor(Color.RED);
                    return;
                }
                if(TextUtils.isEmpty(inst)){
                    institution.setError("Field cannot be empty!");
                    institution.setHintTextColor(Color.RED);
                    return;
                }
                if(TextUtils.isEmpty(yr)){
                    year.setError("Field cannot be empty!");
                    year.setHintTextColor(Color.RED);
                    return;
                }

                Experience e = new Experience(myId,crs,qlf,inst,yr);
                ref.child(id).setValue(e);
                startActivity(new Intent(getApplicationContext(),AccomplishmentActivity.class));
            }
        });
    }
}
