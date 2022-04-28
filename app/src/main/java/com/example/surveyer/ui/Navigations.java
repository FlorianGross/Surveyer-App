package com.example.surveyer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.surveyer.R;
import com.example.surveyer.ui.dashboard.DashboardFragment;
import com.example.surveyer.ui.home.HomeFragment;
import com.example.surveyer.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

public class Navigations extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_navigation, new HomeFragment()).commit();
                    break;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_navigation, new DashboardFragment()).commit();
                    break;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_navigation, new NotificationsFragment()).commit();
                    break;
            }
            return true;
        });
    }

    public static Intent getNavigationIntent(Context context) {
        return new Intent(context, Navigations.class);
    }
}