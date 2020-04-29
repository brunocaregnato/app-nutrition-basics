package com.example.nutritionbasics.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.activities.fragments.Details;
import com.example.nutritionbasics.activities.fragments.Evolution;
import com.example.nutritionbasics.activities.fragments.History;
import com.example.nutritionbasics.activities.fragments.Home;
import com.example.nutritionbasics.activities.fragments.Profile;
import com.example.nutritionbasics.activities.fragments.RegisterMeal;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private Home home;

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
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home()).commit();
                break;
            case R.id.profile:
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Profile()).commit();
                break;
            case R.id.registerMeal:
                Toast.makeText(MainActivity.this, "Register Meal", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RegisterMeal()).commit();
                break;
            case R.id.details:
                Toast.makeText(MainActivity.this, "Details", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Details()).commit();
                break;
            case R.id.history:
                Toast.makeText(MainActivity.this, "History", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new History()).commit();
                break;
            case R.id.evolution:
                Toast.makeText(MainActivity.this, "Evolution", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Evolution()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}