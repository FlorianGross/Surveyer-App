package com.example.surveyer.ui.onBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.surveyer.App;
import com.example.surveyer.R;

public class OnBoarding extends AppCompatActivity {
    FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_activity);
        App.setInForeground(true);

        fragmentContainerView = findViewById(R.id.container);

        changeFragment(new Login());
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }

    public static Intent getOnBoardingIntent(Context context) {
        return new Intent(context, OnBoarding.class);
    }
}