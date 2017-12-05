package com.example.coelab.codetribealumni;

        import android.content.Intent;
        import android.app.ActionBar;
        import android.content.Intent;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*if (user != null) {
            String mail = user.getEmail();
            String[] token = mail.split("@");
            if(token[1].equalsIgnoreCase("mlab.co.za")){
                Intent intent = new Intent(getApplicationContext(),FacilitatorActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                startActivity(intent);
            }
        }*/
    }
    public void sign_in(View view){

        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void sign_up(View view){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }


}
