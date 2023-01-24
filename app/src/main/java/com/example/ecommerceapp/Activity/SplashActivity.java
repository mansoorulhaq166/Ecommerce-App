package com.example.ecommerceapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ecommerceapp.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
   // ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       // progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Objects.requireNonNull(getSupportActionBar()).hide();
        SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.contains("username")) {
                    startActivity(new Intent(SplashActivity.this, Dashboard.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        }, 2000);
    }
}