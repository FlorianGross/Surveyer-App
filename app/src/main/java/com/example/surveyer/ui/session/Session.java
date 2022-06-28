package com.example.surveyer.ui.session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.surveyer.R;
import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.backend.json.SessionJSON;

public class Session extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editName, editDescription;
    SessionViewModel sessionViewModel;
    SocketLiveData socketLiveData;
    SessionJSON session;
    Button btnCreate;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        sessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        socketLiveData = sessionViewModel.getSocketLiveData();
        session = new SessionJSON();
        recyclerView = findViewById(R.id.participants_recycler);
        editName = findViewById(R.id.nameOfSessionEdit);
        editDescription = findViewById(R.id.sessionDescriptionEdit);
        btnCreate = findViewById(R.id.createSessionButton);

        if (id == null){
            btnCreate.setText("Create Session");
            btnCreate.setOnClickListener(v -> {
                session.name = editName.getText().toString();
                session.description = editDescription.getText().toString();
            });
        }else{
            btnCreate.setText("Update Session");
            btnCreate.setOnClickListener(v -> {
                session.name = editName.getText().toString();
                session.description = editDescription.getText().toString();
            });
        }
    }
}