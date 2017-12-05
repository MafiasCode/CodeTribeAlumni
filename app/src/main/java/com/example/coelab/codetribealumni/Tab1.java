package com.example.coelab.codetribealumni;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Laser on 11/13/2017.
 */

public class Tab1 extends Fragment implements View.OnClickListener {
    private EditText txtName, txtSurname, txtCellphone, txtEmail, txtGender, txtRole, txtLocation, txtYear;
    String name, surname, cellphone, email, gender, role, location, year;
    ImageView stdPicture;
    private Button btn_stdUpdate;
    private AppCompatSpinner spn_gender, spn_year, spn_role, spn_location;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FloatingActionButton fab;
    String id;


    private int PICK_IMAGE_REQUEST = 8;
    private Uri filePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);

        //find views
        fab = (FloatingActionButton) view.findViewById(R.id.btnEditPros);
        fab.setOnClickListener(this);
        txtGender = (EditText) view.findViewById(R.id.txt_profile_gender);
        txtRole = (EditText) view.findViewById(R.id.txt_profile_role);
        txtLocation = (EditText) view.findViewById(R.id.txt_profile_location);
        txtYear = (EditText) view.findViewById(R.id.txt_profile_year);
        txtName = (EditText) view.findViewById(R.id.txt_profile_name);
        txtSurname = (EditText) view.findViewById(R.id.txt_profile_surname);
        txtCellphone = (EditText) view.findViewById(R.id.txt_profile_phoneNo);
        txtEmail = (EditText) view.findViewById(R.id.txt_profile_email);
        spn_gender = view.findViewById(R.id.genderSpinner);
        spn_year = view.findViewById(R.id.yearSpinner);
        spn_role = view.findViewById(R.id.roleSpinner);
        spn_location = view.findViewById(R.id.locationSpinner);
        btn_stdUpdate = view.findViewById(R.id.update_stdprofile);
        stdPicture = view.findViewById(R.id.profile_stdupdate_image);
        btn_stdUpdate.setOnClickListener(this);

        //Database connection
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        id = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("Userprofiles").child(id);
        storageReference = firebaseStorage.getReference().child("UserProfile_photos").child(id);

        //making the spinners invisible
        spn_gender.setVisibility(View.GONE);
        spn_role.setVisibility(View.GONE);
        spn_year.setVisibility(View.GONE);
        spn_location.setVisibility(View.GONE);
        btn_stdUpdate.setVisibility(View.GONE);

        // for spinner

        final ArrayAdapter<CharSequence> genderAdapter;
        final ArrayAdapter<CharSequence> locationAdapter;
        final ArrayAdapter<CharSequence> roleAdapter;
        final ArrayAdapter<CharSequence> yearAdapter;

        locationAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.location, android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_location.setAdapter(locationAdapter);

        genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_gender.setAdapter(genderAdapter);

        // geting the picture from gallary
        stdPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK, android.provider.
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallaryIntent, PICK_IMAGE_REQUEST);
            }
        });

        // View a picture from firebase
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                //img_profileUpload
                displayprofilePicture(uri);

            }
        });

        //Retrieve data from firebase
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Person p = dataSnapshot.getValue(Person.class);
                if (p != null) {


                    txtName.setText(p.getName());
                    txtSurname.setText(p.getSurname());
                    txtCellphone.setText(p.getCell());
                    txtEmail.setText(p.getEmail());
                    txtGender.setText(p.getGender());
                    txtLocation.setText(p.getLocation());
                    txtRole.setText(p.getRole());
                    txtYear.setText(p.getYear());

                    // set value to spinner
                    for (int g = 0; g < spn_gender.getCount(); g++){
                        if (spn_gender.getItemAtPosition(g).toString().equals(p.getGender())) {
                            spn_gender.setSelection(g);
                            break;
                        }
                    }

                    for (int l = 0; l < spn_location.getCount(); l++){
                        if (spn_location.getItemAtPosition(l).toString().equals(p.getGender())) {
                            spn_location.setSelection(l);
                            break;
                        }
                    }


                } else {
                    Toast.makeText(getContext(), "Empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {


            //Toast.makeText(getContext(), "Editable", Toast.LENGTH_SHORT).show();
            txtName.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            txtSurname.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            txtCellphone.setInputType(InputType.TYPE_CLASS_PHONE);
            txtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            txtGender.setVisibility(View.GONE);
            //txtYear.setVisibility(View.GONE);
            txtLocation.setVisibility(View.GONE);
            //txtRole.setVisibility(View.GONE);
            spn_gender.setVisibility(View.VISIBLE);
            //spn_role.setVisibility(View.VISIBLE);
            //spn_year.setVisibility(View.VISIBLE);
            spn_location.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            btn_stdUpdate.setVisibility(View.VISIBLE);

            /*
            String[] genders = {"Choose gender", "Male", "Female"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, genders);
            spn_gender.setAdapter(adapter1);

            String[] roles = {"Choose role", "Facilitator", "Student"};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roles);
            spn_role.setAdapter(adapter2);

            String[] years = {"Choose year", "2017", "2018", "2019", "2020", "2021", "2022"};
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
            spn_year.setAdapter(adapter3);

            String[] locations = {"Choose location", "Tshwane", "Alexandra", "Soweto", "Tembisa"};
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, locations);
            spn_location.setAdapter(adapter4); */

        }

        //Update student
        if (view == btn_stdUpdate){

            name = txtName.getText().toString();
            surname = txtSurname.getText().toString();
            cellphone = txtCellphone.getText().toString();
            email = txtEmail.getText().toString();
            gender = spn_gender.getSelectedItem().toString();
            //role = spn_role.getSelectedItem().toString();
            location = spn_location.getSelectedItem().toString();
            //year = spn_year.getSelectedItem().toString();


            if (filePath != null){
                storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri profilePhotoUrl = taskSnapshot.getDownloadUrl();
                        //Log.i("photourl", profilePhotoUrl.toString());
                        //databaseReference.child("photoUrl").setValue(profilePhotoUrl);
                        //Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //Toast.makeText(getApplication(), "Not uploaded", Toast.LENGTH_SHORT).show();
            }

            if (! TextUtils.isEmpty(name)){
                ref.child("name").setValue(name);
            }else {
                //dialog.dismiss();
                txtName.setError("Field cannot be empty!");
                txtName.setHintTextColor(Color.RED);
                return;
            }

            if ( !TextUtils.isEmpty(surname)){
                ref.child("surname").setValue(surname);

            }else
            {
                txtSurname.setError("Field cannot be empty!");
                txtName.setHintTextColor(Color.RED);
                return;
            }

            if ( !TextUtils.isEmpty(cellphone)){
                ref.child("cell").setValue(cellphone);

            }else
            {
                txtSurname.setError("Field cannot be empty!");
                txtName.setHintTextColor(Color.RED);
                return;
            }

            if (!gender.equals("Choose gender")){

                ref.child("gender").setValue(gender);
            } else
            {
                Toast.makeText(getContext(),"Please select  gender",Toast.LENGTH_SHORT).show();
                return;
            }

            if (!location.equals("Choose location")) {

                ref.child("location").setValue(location);
            }else{
                Toast.makeText(getContext(),"Please select codetribe location",Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

            spn_gender.setVisibility(View.GONE);
            spn_location.setVisibility(View.GONE);
            txtGender.setVisibility(View.VISIBLE);
            txtLocation.setVisibility(View.VISIBLE);


            btn_stdUpdate.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            filePath = data.getData();
            try {

                //Bitmap bitmap = BitmapFactory.decodeFile(filePath.getEncodedPath());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                stdPicture.setImageBitmap( bitmap );

                //displayprofilePicture(profilePhotoUrl);

            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void displayprofilePicture(Uri downloadUrl){
        if (downloadUrl != null){
            Picasso.with(getContext()).load(downloadUrl)
                    .transform(new PicassoCircleTransformation()).fit().centerCrop().into(stdPicture);
        }
    }


}