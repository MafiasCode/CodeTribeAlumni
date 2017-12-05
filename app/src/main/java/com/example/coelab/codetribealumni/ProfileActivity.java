package com.example.coelab.codetribealumni;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import org.w3c.dom.Text;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Codetribe on 11/9/2017.
 */

public class ProfileActivity extends AppCompatActivity
{

    ImageView img_profileUpload;
    EditText edt_name;
    EditText edt_surname;
    EditText edt_id;
    EditText edt_dob;
    EditText edt_birthCountry;
    EditText edt_mobile;
    EditText edt_email;
    EditText edt_role;
    Spinner spn_role;
    Spinner spn_year;
    Spinner spn_location;
    Spinner spn_gender;
    Button btn_updateProfile;

    String name,surname,contact,email,password,gender,location,year,role;
    private String uid;
    private Person person;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 7;
    String imgDecodableString;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri profilePhotoUrl;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseUser firebaseUser;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize the view
        img_profileUpload = (ImageView) findViewById(R.id.profile_update_image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.blank_image1);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        img_profileUpload.setImageDrawable(roundedBitmapDrawable);

        edt_name = (EditText) findViewById(R.id.txt_profile_name);
        edt_surname = (EditText) findViewById(R.id.txt_profile_surname);
        edt_mobile = (EditText) findViewById(R.id.txt_profile_phoneNo);
        edt_email = (EditText)findViewById(R.id.txt_profile_email);
        edt_role = (EditText)findViewById(R.id.txt_role);
        spn_gender = (Spinner)findViewById(R.id.spn_gender);
       //spn_role = (Spinner) findViewById(R.id.roleSpinner);
        spn_location = (Spinner)findViewById(R.id.spn_location);
        edt_dob = (EditText)findViewById(R.id.txt_dob);

        btn_updateProfile = (Button)findViewById(R.id.update_profile);

        // creating Instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();


        uid = firebaseUser.getUid();

        databaseReference = firebaseDatabase.getReference().child("Userprofiles").child(uid);
        storageReference = firebaseStorage.getReference().child("UserProfile_photos").child(uid);


        // for spinner
        final ArrayAdapter<CharSequence> genderAdapter;
        final ArrayAdapter<CharSequence> locationAdapter;
        final ArrayAdapter<CharSequence> roleAdapter;
        final ArrayAdapter<CharSequence> yearAdapter;

        locationAdapter = ArrayAdapter.createFromResource(getApplication(), R.array.location, android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_location.setAdapter(locationAdapter);

        roleAdapter = ArrayAdapter.createFromResource(getApplication(), R.array.role, android.R.layout.simple_spinner_dropdown_item);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn_role.setAdapter(roleAdapter);

        yearAdapter = ArrayAdapter.createFromResource(getApplication(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spn_year.setAdapter(yearAdapter);

        genderAdapter = ArrayAdapter.createFromResource(getApplication(), R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_gender.setAdapter(genderAdapter);

        edt_name.setFocusable(false);
        edt_surname.setFocusable(false);
        edt_mobile.setFocusable(false);
        edt_email.setEnabled(false);
        edt_role.setEnabled(false);
        spn_gender.getSelectedView();
        spn_gender.setEnabled(false);
        spn_location.setEnabled(false);
        btn_updateProfile.setVisibility(View.INVISIBLE);


        // uploading an image
        img_profileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create intent to Open
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK,
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



       final  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

                edt_name.setFocusable(true);
                edt_name.setFocusableInTouchMode(true);
                edt_surname.setFocusable(true);
                edt_surname.setFocusableInTouchMode(true);
                edt_mobile.setFocusable(true);
                edt_mobile.setFocusableInTouchMode(true);
                spn_gender.setEnabled(true);
                spn_location.setEnabled(true);

                btn_updateProfile.setVisibility(View.VISIBLE);

                fab.setVisibility(View.INVISIBLE);
            }
        });

        // Retrieve information from firebase and load it on edittext
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Person persons = dataSnapshot.getValue(Person.class);

                if (persons != null) {

                    edt_name.setText(persons.getName());
                    edt_surname.setText(persons.getSurname());
                    edt_mobile.setText(persons.getCell());
                    edt_email.setText(persons.getEmail());
                    edt_role.setText(persons.getRole());
                    //edt_role.setTextColor(Integer.parseInt(persons.getPhotoUrl()));
                    //Log.i()

                    // set value to spinner
                    for (int g = 0; g < spn_gender.getCount(); g++){
                        if (spn_gender.getItemAtPosition(g).toString().equals(persons.getGender())) {
                            spn_gender.setSelection(g);
                            break;
                        }
                    }

                    for (int l = 0; l < spn_location.getCount(); l++ ){
                        if (spn_location.getItemAtPosition(l).toString().equals(persons.getLocation())){
                            spn_location.setSelection(l);
                            break;
                        }
                    }

                }
                else {
                    Toast.makeText(getApplication(), "Sorry there seem to be no information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //button to update the user profile
        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = edt_name.getText().toString();
                surname = edt_surname.getText().toString();
                contact = edt_mobile.getText().toString();
                gender = spn_gender.getSelectedItem().toString();
                location =spn_location.getSelectedItem().toString();

                if (filePath != null){
                    storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri profilePhotoUrl = taskSnapshot.getDownloadUrl();
                            Log.i("photourl", profilePhotoUrl.toString());
                            //databaseReference.child("photoUrl").setValue(profilePhotoUrl);
                            Toast.makeText(getBaseContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //Toast.makeText(getApplication(), "Not uploaded", Toast.LENGTH_SHORT).show();
                }


                if (! TextUtils.isEmpty(name)){
                    databaseReference.child("name").setValue(name);
                }else {
                    //dialog.dismiss();
                    edt_name.setError("Field cannot be empty!");
                    edt_name.setHintTextColor(Color.RED);
                    return;
                }

                if (!TextUtils.isEmpty(surname)) {
                    databaseReference.child("surname").setValue(surname);
                }
                else
                {
                    edt_surname.setError("Field cannot be empty!");
                    edt_surname.setTextColor(Color.RED);
                    return;
                }

                if (!TextUtils.isEmpty(contact)){
                    databaseReference.child("cell").setValue(contact);
                }else{
                    edt_mobile.setError("Field cannot be Empty");
                    edt_mobile.setTextColor(Color.RED);
                    return;
                }

                if (!gender.equals("Select gender")){

                    databaseReference.child("gender").setValue(gender);
                } else
                {
                    Toast.makeText(getBaseContext(),"Please select gender",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!spn_location.equals("Select codetribe location")) {

                    databaseReference.child("location").setValue(location);
                }else{
                    Toast.makeText(getBaseContext(),"Please select codetribe locationr",Toast.LENGTH_SHORT).show();
                    return;
                }

                btn_updateProfile.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);

                Toast.makeText(ProfileActivity.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();

                /* de activate the text
                edt_name.setEnabled(false);
                edt_surname.setEnabled(false);
                edt_mobile.setEnabled(false);
                edt_email.setEnabled(false);
                edt_role.setEnabled(false);
                spn_gender.setEnabled(false);
                spn_location.setEnabled(false); */

                // de activate the text
                edt_name.setFocusable(false);
                edt_surname.setFocusable(false);
                edt_mobile.setFocusable(false);
                edt_email.setEnabled(false);
                edt_role.setEnabled(false);
                spn_gender.setEnabled(false);
                //spn_gender.setClickable(false);
                spn_location.setEnabled(false);
                btn_updateProfile.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            filePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img_profileUpload.setImageBitmap(bitmap);

               //displayprofilePicture(profilePhotoUrl);

            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        // Convert file path into bitmap image using below line.

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        //Bitmap CircleBitmap = getRoundedCornerBitmap(bitmap, 100);

        return bitmap;

    }

    private void displayprofilePicture(Uri downloadUrl){
        if (downloadUrl != null){
            Picasso.with(getApplication()).load(downloadUrl)
                    .transform(new PicassoCircleTransformation()).fit().centerCrop().into(img_profileUpload);
        }
    }


}
