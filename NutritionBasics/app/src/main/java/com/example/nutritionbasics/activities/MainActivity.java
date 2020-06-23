package com.example.nutritionbasics.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.Toast;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.activities.fragments.Home;
import com.example.nutritionbasics.banco.Database;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private String menuOption;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.registerMeal, R.id.profile, R.id.history, R.id.evolution, R.id.info, R.id.update)
                .setDrawerLayout(drawerLayout)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> menuItemSelected(item));


        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new Home()).commit();
    }


    private boolean menuItemSelected(@NonNull MenuItem menuItem) {
        Database bd = new Database(this);
        boolean handled = false;
        menuOption = String.valueOf(menuItem.getItemId());

        if(bd.getUser() == null) {
            Toast.makeText(MainActivity.this,"Register your Profile to use the app!", Toast.LENGTH_LONG).show();
        }
        else {
            handled = NavigationUI.onNavDestinationSelected(menuItem, navController);
            if (handled) {
                ViewParent parent = navigationView.getParent();
                if (parent instanceof DrawerLayout) {
                    ((DrawerLayout) parent).closeDrawer(navigationView);
                }
            }
        }

        return handled;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
