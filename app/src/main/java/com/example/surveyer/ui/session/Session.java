package com.example.surveyer.ui.session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.surveyer.R;
import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.backend.helper.ImageRequester;
import com.example.surveyer.backend.helper.QRCodeHelper;
import com.example.surveyer.backend.helper.SurveyHelper;
import com.example.surveyer.backend.json.PayloadJSON;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.example.surveyer.ui.dashboard.DashboardAdapter;
import com.example.surveyer.ui.notifications.Fragment_Survey;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Session extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editName, editDescription;
    SessionViewModel sessionViewModel;
    ImageView image;
    SocketLiveData socketLiveData;
    SessionJSON session;
    Button btnCreate;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        if (getIntent().getStringExtra("sessionId") != null) {
            id = getIntent().getStringExtra("sessionId");
        }
        sessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        socketLiveData = sessionViewModel.getSocketLiveData();
        socketLiveData.observe(this, socketEventModelObserver);
        socketLiveData.connect();
        session = new SessionJSON();
        recyclerView = findViewById(R.id.participants_recycler);
        editName = findViewById(R.id.nameOfSessionEdit);
        editDescription = findViewById(R.id.sessionDescriptionEdit);
        btnCreate = findViewById(R.id.createSessionButton);
        image = findViewById(R.id.imageView5);

        if (id == null) {
            btnCreate.setText("Create Session");
            btnCreate.setOnClickListener(v -> {
                session.name = editName.getText().toString();
                session.description = editDescription.getText().toString();
                JsonObject obj = new JsonObject();
                obj.addProperty("name", session.name);
                obj.addProperty("description", session.description);
                obj.addProperty("uid", PreferenceUtil.getDeviceId());
                sessionViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_CREATESESSION, obj)
                        )
                );
            });
        } else {
            getAllSessions();
            setImage();
            btnCreate.setText("Update Session");
            btnCreate.setOnClickListener(v -> {
                session.name = editName.getText().toString();
                session.description = editDescription.getText().toString();
                sessionViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_UPDATESESSION, session)));
            });
        }
    }

    void getAllSessions() {
        JsonObject object = new JsonObject();
        object.addProperty("sessionID", id);
        sessionViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETSESSIONFROMID, object)));
    }

    void setImage() {
        ImageRequester imageRequester = new ImageRequester();
        imageRequester.execute(QRCodeHelper.generateBarCode(id), image);
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(Fragment_Survey.class, "getSocket: " + socketEventModel.toString());
        String toJSONString = socketEventModel.getPayloadAsString();
        try {
            JSONObject jsonObject = new JSONObject(toJSONString);
            if (jsonObject.has("event") && jsonObject.getString("result").equals("Session")) {
                session = SurveyHelper.getSessionFromJSONOBject(jsonObject);
                System.out.println("Session " + session.toString());
                editName.setText(session.name);
                editDescription.setText(session.description);
                setImage();
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    };
}
