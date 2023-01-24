package com.example.ecommerceapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Objects.requireNonNull(getSupportActionBar()).hide();

        drawerLayout = findViewById(R.id.myDrawerLayout);
        navigationView = findViewById(R.id.nav_menu_view);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open,
                R.string.Close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_cart: {
                        startActivity(new Intent(Dashboard.this, RoomActivity.class));
                        break;
                    }
                    case R.id.nav_logout: {
                        SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(Dashboard.this, SplashActivity.class));
                        finish();
                        //Toast.makeText(Dashboard.this, "You Clicked On Logout", Toast.LENGTH_SHORT).show();
                        //drawerLayout.close();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}