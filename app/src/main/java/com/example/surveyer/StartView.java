package com.example.surveyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class StartView extends AppCompatActivity {
    EditText sessionId;
    Button startSession, joinSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_view);
        startSession = findViewById(R.id.startSessionButton);
        joinSession = findViewById(R.id.joinSessionButton);
        sessionId = findViewById(R.id.SessionId);


        startSession.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminView.class);
            startActivity(intent);
        });
        joinSession.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserView.class);
            intent.putExtra("id", sessionId.getText().toString());
            startActivity(intent);
        });
    }
}