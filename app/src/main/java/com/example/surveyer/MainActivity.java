package com.example.surveyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        Intent intent = new Intent(this, SurveyView.class);
        startActivity(intent);

    }

    private void initSharedPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userID = prefs.getString("uid", null);
        if(userID == null){
            helper.registerUser();
        }
    }


}