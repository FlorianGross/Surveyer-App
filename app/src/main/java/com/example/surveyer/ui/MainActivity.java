package com.example.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.ui.onboarding.OnBoarding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.setInForeground(true);

        new Handler().postDelayed(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startActivity(OnBoarding.getOnBoardingIntent(MainActivity.this));
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}