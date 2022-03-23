package com.example.surveyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

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
        initSharedPreferences();
        initSocketConnection();

    }

    private void initSharedPreferences() {
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       userID = prefs.getString("uid", null);
       sessionID = prefs.getString("session", null);
    }

    private void initSession() {
        System.out.println("Init Session");
        try {
            JSONObject jo = new JSONObject();
            jo.put("Type", "startSession");
            webSocket.send(jo.toString());
            System.out.println(jo);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    private void initSocketConnection() {
        System.out.println("Init Socket Connection");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(WebSocketHelper.getWifiIp(getApplicationContext())).build();
        webSocket = client.newWebSocket(request, new AdminView.adminViewListener());
    }

    private void leavePage() {
        System.out.println("Leave page");
        Intent intent = new Intent(this, StartView.class);
        startActivity(intent);
    }

    private class adminViewListener extends WebSocketListener {

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            System.out.println("Output" + text);
            super.onMessage(webSocket, text);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(text);
            }catch (Exception e){
                System.out.println("Error parsing JSONObject: " + e);
            }
            System.out.println(jsonObject);
            runOnUiThread(() -> adminText.setText(text));
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
            System.out.println("Connection opened" + response);
        }
    }
}
