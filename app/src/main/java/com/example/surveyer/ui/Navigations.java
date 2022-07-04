package com.example.surveyer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.ui.dashboard.DashboardFragment;
import com.example.surveyer.ui.home.HomeFragment;
import com.example.surveyer.ui.notifications.Fragment_Survey;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

public class Navigations extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private final SocketLiveData socketLiveData = SocketLiveData.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        App.setInForeground(true);
        socketLiveData.connect();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_navigation, new Fragment_Survey()).commit();
                    break;
            }
            return true;
        });
    }

    public static Intent getNavigationIntent(Context context) {
        return new Intent(context, Navigations.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }
}