package com.floriang.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.floriang.surveyer.App;
import com.floriang.surveyer.R;
import com.floriang.surveyer.backend.util.PreferenceUtil;
import com.floriang.surveyer.ui.onBoarding.OnBoarding;

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