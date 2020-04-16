package com.example.nutritionbasics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
                break;
            case R.id.profile:
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.registerMeal:
                Toast.makeText(MainActivity.this, "Register Meal", Toast.LENGTH_LONG).show();
                break;
            case R.id.details:
                Toast.makeText(MainActivity.this, "Details", Toast.LENGTH_LONG).show();
                break;
            case R.id.history:
                Toast.makeText(MainActivity.this, "History", Toast.LENGTH_LONG).show();
                break;
            case R.id.evolution:
                Toast.makeText(MainActivity.this, "Evolution", Toast.LENGTH_LONG).show();
                break;


        }
        return false;
    }
}
