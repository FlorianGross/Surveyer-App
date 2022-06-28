package com.example.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.Onboarding.OnBoarding;

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
            Intent intent;
            if(PreferenceUtil.getDeviceId() != null){
                intent = new Intent(this, Navigations.class);
            }else{
                intent = new Intent(this, OnBoarding.class);
            }
            startActivity(intent);
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }
}