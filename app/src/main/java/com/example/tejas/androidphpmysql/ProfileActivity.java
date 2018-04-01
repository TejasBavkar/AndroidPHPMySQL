package com.example.tejas.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewUsername, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        textViewUsername = (TextView)findViewById(R.id.text_view_profile_username);
        textViewEmail = (TextView)findViewById(R.id.text_view_profile_email);

        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());

    }
}
