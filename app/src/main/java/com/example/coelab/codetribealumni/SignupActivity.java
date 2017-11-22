package com.example.coelab.codetribealumni;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends Activity
{

    private Spinner locationSpinner,rolePinner,genderSpinner,yearSpinner;
    private EditText edName,edSurname,edContactdetails,edEmail,edPassword;
    private Button btnSignup;
    String name,surname,contact,email,password,gender,location,year,role;

    //Firebase
    private ProgressDialog dialog;
    private String id;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Database reference
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        //Dialog initializing
        dialog = new ProgressDialog(this);
        dialog.setTitle("Registering");
        dialog.setMessage("Please wait...");

        //Initializing the fields
        locationSpinner =(Spinner) findViewById(R.id.locationSpinner);
        yearSpinner =(Spinner) findViewById(R.id.yearSpinner);
        genderSpinner =(Spinner) findViewById(R.id.genderSpinner);
        rolePinner =(Spinner) findViewById(R.id.roleSpinner);
        edName = (EditText)findViewById(R.id.txtName);
        edSurname = (EditText)findViewById(R.id.txtSurname);
        edContactdetails = (EditText)findViewById(R.id.txtContactdetails);
        edEmail = (EditText)findViewById(R.id.txtEmail);
        edPassword = (EditText) findViewById(R.id.txtPassword);
        btnSignup = (Button) findViewById(R.id.signup);

        //Spinner
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.location));
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.role));
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rolePinner.setAdapter(roleAdapter);

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.year));
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.gender));
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        //Database references
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Userprofiles");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null)
                {
                    id = user.getUid();
                    name = edName.getText().toString();
                    surname = edSurname.getText().toString();
                    contact = edContactdetails.getText().toString();
                    email = edEmail.getText().toString();
                    password = edPassword.getText().toString();
                    gender = genderSpinner.getSelectedItem().toString();
                    location = locationSpinner.getSelectedItem().toString();
                    role = rolePinner.getSelectedItem().toString();
                    year = yearSpinner.getSelectedItem().toString();
                    Person person = new Person(mAuth.getCurrentUser().getUid(),name, surname,contact, gender,email, role,location,year);
                    myRef.child(id).setValue(person);
                }
            }
        };

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount()
    {
        dialog.show();
        String useremail = edEmail.getText().toString();
        String userpassword = edPassword.getText().toString();

        //Tryng something out=====================================
        name = edName.getText().toString();
        surname = edSurname.getText().toString();
        contact = edContactdetails.getText().toString();
        email = edEmail.getText().toString();
        password = edPassword.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
        location = locationSpinner.getSelectedItem().toString();
        role = rolePinner.getSelectedItem().toString();
        year = yearSpinner.getSelectedItem().toString();

        //Validating the location spinner
        //LOCATION
        if(location.equals("Thembisa"))
        {
            location = "Thembisa";
        }
        else if(location.equals("Soweto"))
        {
            location = "Soweto";
        }
        else if(location.equals("Tshwane"))
        {
            location = "Tshwane";
        }
        else if(location.equals("Alexandra"))
        {
            location ="Alexandra";
        }
        else if(location.equals("Select codetribe location"))
        {
            dialog.dismiss();
            Toast.makeText(getBaseContext(),"Please select codetribe location",Toast.LENGTH_SHORT).show();
            return;
        }

        //=========================================================
        //YEARS
        if(year.equals("2018"))
        {
            year = "2018";
        }
        else if(year.equals("Select year"))
        {
            dialog.dismiss();
           Toast.makeText(getBaseContext(),"Please select year",Toast.LENGTH_SHORT).show();
           return;
        }
        //=========================================================
        //ROLE
        if(role.equals("Student"))
        {
            role = "Student";
        }
        else if(role.equals("Facilitator"))
        {
            role = "Facilitator";
        }
        else if(role.equals("Select role"))
        {
            dialog.dismiss();
            Toast.makeText(getBaseContext(),"Please select role",Toast.LENGTH_SHORT).show();
            return;
        }
        //=========================================================
        //GENDER
        if(gender.equals("Male"))
        {
            String sex = "Male";
        }
        else if(gender.equals("Female"))
        {
            String sex = "Female";
        }
        else if(gender.equals("Select gender"))
        {
            dialog.dismiss();
            Toast.makeText(getBaseContext(),"Please select gender",Toast.LENGTH_SHORT).show();
            return;
        }

        //Validations
        if (TextUtils.isEmpty(name))
        {
            dialog.dismiss();
            edName.setError("Field must not be empty");
            return;
        }

        for (int i = 0; i < name.length(); i++)
        {
            dialog.dismiss();
            if (Character.isDigit(name.charAt(i)) || !Character.isLetterOrDigit(name.charAt(i))) {
                edName.setError("Please enter only letters");
                return;
            }
        }

        //Validate surname
        if (TextUtils.isEmpty(name))
        {
            dialog.dismiss();
            edSurname.setError("Field must not be empty");
            return;
        }

        for (int i = 0; i < surname.length(); i++)
        {
            dialog.dismiss();
            if (Character.isDigit(surname.charAt(i)) || !Character.isLetterOrDigit(surname.charAt(i))) {
                edSurname.setError("Please enter only letters");
                return;
            }
        }

        //Validate cell number
        if (TextUtils.isEmpty(contact))
        {
            dialog.dismiss();
            edContactdetails.setError("Field must not be empty");
            return;
        }

        if (isPhoneValid(contact) == false || contact.length() < 10 || contact.length() > 10)
        {
            dialog.dismiss();
            edContactdetails.setError("Please enter valid phone numbers");
            return;
        }

        for (int i = 0; i < contact.length(); i++)
        {
            dialog.dismiss();
            if (Character.isWhitespace(contact.charAt(i))) {
                edContactdetails.setError("No space required");
                return;
            }
        }

        //Validate the email
        if (TextUtils.isEmpty(email))
        {
            dialog.dismiss();
            edEmail.setError("Field must not be empty");
            return;
        }

        if (isEmailValid(email) == false)
        {
            dialog.dismiss();
            edEmail.setError("Please enter the correct email");
            return;
        }


        //=========================================================

        //Checking if the emai its not empty
        if(TextUtils.isEmpty(useremail))
        {
            dialog.dismiss();
            edEmail.setError("Field cannot be empty!");
            edEmail.setHintTextColor(Color.RED);
            return;
        }

        //Validating the email
        if(!isEmailValid(useremail)){
            dialog.dismiss();
            edEmail.setError("Invalid email address!");
            edEmail.setHintTextColor(Color.RED);
            return;
        }

        //Checking if the emai its not empty
        if(TextUtils.isEmpty(userpassword))
        {
            dialog.dismiss();
            edPassword.setError("Field empty - Please enter email");
            edPassword.setHintTextColor(Color.RED);
            return;
        }

        //validating if the password does not contain a space
        for(int i = 0;i <userpassword.length();i++)
        {
            if(Character.isWhitespace(userpassword.charAt(i)))
            {
                dialog.dismiss();
                edPassword.setError("Password must not contain spaces");
                return;
            }
        }

        mAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                id = mAuth.getCurrentUser().getUid();
                                Intent intent = new Intent(getBaseContext(),SignInActivity.class);
                                intent.putExtra("Id",id);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this, "Oops! something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    edEmail.setText("");
                    return;
                }
                dialog.dismiss();
            }
        });
    }

    //Method for validating the email
    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    //Method for validation the contact details
    public boolean isPhoneValid(String phone)
    {
        boolean isValid = false;

        String[] validNum = {"076","071","082","083","073","072","081","079","061","062","060","063","074","078","084","080","086","088","085","077","075","087","089"};

        String num = phone.substring(0, 3);


        for(int x = 0;x<validNum.length;x++)
        {
            if(num.equalsIgnoreCase(validNum[x]))
            {
                isValid = true;
            }
        }
        return isValid;
    }

    public void navigateToLoginActivity(View view)
    {
        Intent intent = new Intent(getBaseContext(),SignInActivity.class);
        startActivity(intent);
    }

}
