package com.example.nutritionbasics.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.activities.fragments.Details;
import com.example.nutritionbasics.activities.fragments.Evolution;
import com.example.nutritionbasics.activities.fragments.History;
import com.example.nutritionbasics.activities.fragments.Home;
import com.example.nutritionbasics.activities.fragments.Info;
import com.example.nutritionbasics.activities.fragments.Profile;
import com.example.nutritionbasics.activities.fragments.RegisterMeal;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.User;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private Home home;
    private boolean backPressedToExitOnce;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BDfood bd = new BDfood(this);

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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Home()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        BDfood bd = new BDfood(this);
        if(bd.getUser() == null) {
            Toast.makeText(MainActivity.this,"Register your Profile to use the app!", Toast.LENGTH_LONG).show();
        }
        else {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Home()).commit();
                    break;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Profile()).commit();
                    break;
                case R.id.registerMeal:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new RegisterMeal()).commit();
                    break;
                case R.id.history:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new History()).commit();
                    break;
                case R.id.evolution:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Evolution()).commit();
                    break;
                case R.id.info:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Info()).commit();
                    break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    super.onBackPressed();
                    return;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Exit Nutrition Basics?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
