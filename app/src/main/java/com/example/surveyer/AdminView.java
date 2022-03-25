package com.example.surveyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.surveyer.backend.WebSocketHelper;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class AdminView extends AppCompatActivity {
    private String userID;
    private String sessionID;
    private WebSocket webSocket;
    TextView adminText;
    Button adminbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

    }

    private void leavePage() {
        System.out.println("Leave page");
        Intent intent = new Intent(this, StartView.class);
        startActivity(intent);
    }
}
