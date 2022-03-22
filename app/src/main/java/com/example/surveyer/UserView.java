package com.example.surveyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class UserView extends AppCompatActivity {
    private String id;
    private WebSocket webSocket;
    private String SERVER_PATH = "localhost:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        id = getIntent().getStringExtra("id");
        initSocketConnection();
    }

    private void initSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());
    }
    private class SocketListener extends WebSocketListener{
        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
            runOnUiThread(()->{
                Toast.makeText(UserView.this, "Socket connection successful", Toast.LENGTH_LONG).show();
            initializeView();
            });
        }
    }
    private void initializeView() {

    }
}