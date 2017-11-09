package com.example.coelab.codetribealumni;

        import android.content.Intent;
        import android.app.ActionBar;
        import android.content.Intent;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void sign_in(View view){

        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void sign_up(View view){
        //Intent intent = new Intent(this,SignUpActivity.class);
        //startActivity(intent);
    }

    public void about_us (View view){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }
}
