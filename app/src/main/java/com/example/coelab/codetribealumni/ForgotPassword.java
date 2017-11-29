package com.example.coelab.codetribealumni;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private EditText resetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetpassword = (EditText) findViewById(R.id.txtPasswordReset);
    }

    public void resetpasswords(View view) {
        String resPassword = resetpassword.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = resPassword;
        if (isEmailValid(emailAddress)) {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("ForgotPassword", "Email sent.");
                                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), "Sorry, Your request was unsuccessful", Toast.LENGTH_LONG).show();
                                resetpassword.setHintTextColor(Color.RED);
                                resetpassword.setError("Check your email address");
                            }
                        }
                    });
        } else {
            resetpassword.setError("Invalid email address");
            resetpassword.setHintTextColor(Color.RED);
            //Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }

    public void navigate(View view) {
        startActivity(new Intent(ForgotPassword.this, SignInActivity.class));
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }
}
