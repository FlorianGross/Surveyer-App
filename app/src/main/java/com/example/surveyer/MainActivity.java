package com.example.surveyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.surveyer.backend.WebSocketHelper;

public class MainActivity extends AppCompatActivity {
    String userID;
    WebSocketHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = WebSocketHelper.getInstance();
        helper.connectToSocket(getApplicationContext());

        initSharedPreferences();

        new Handler().postDelayed(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent mainIntent = new Intent(MainActivity.this,Navigations.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }, 1000);

    }

    private void initSharedPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userID = prefs.getString("uid", null);
        if(userID == null){
           // helper.registerUser();
        }
    }


}