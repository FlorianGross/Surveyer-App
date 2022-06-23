package com.example.surveyer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.onboarding.OnBoarding;
import com.example.surveyer.ui.survey.Survey_Create;
import com.example.surveyer.backend.service.ForegroundService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!foregroundServiceRunning()){
            Intent service = new Intent(this, ForegroundService.class);
            startForegroundService(service);
        }

        new Handler(Looper.getMainLooper()).postDelayed(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(PreferenceUtil.getDeviceId() != null){
                startActivity(Navigations.getNavigationIntent(this));
            }else{
                startActivity(OnBoarding.getOnBoardingIntent(this));
            }
        }, 1000);

    }
    @SuppressWarnings("deprecation")
    public boolean foregroundServiceRunning() {
        ActivityManager managert = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : managert.getRunningServices(Integer.MAX_VALUE)) {
            if (ForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}