package com.example.surveyer.ui.session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.example.surveyer.ui.notifications.Fragment_Survey;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Session extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editName, editDescription;
    SessionViewModel sessionViewModel;
    ImageView image;
    Toast toast;
    SocketLiveData socketLiveData;
    SessionJSON session;
    String[] participants = {};
    Button btnCreate;
    String id = null;
    Boolean isOwner = false;

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
        recyclerView.setAdapter(new SessionAdapter(participants, isOwner));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            getIsOwner();
            getSession();
            setImage();
            btnCreate.setText("Update Session");
            btnCreate.setOnClickListener(v -> {
                session.name = editName.getText().toString();
                session.description = editDescription.getText().toString();
                sessionViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_UPDATESESSION, session)));
            });
        }
    }

    void getIsOwner(){
        if(!isOwner){
            editName.setEnabled(false);
            editDescription.setEnabled(false);
            btnCreate.setVisibility(View.GONE);
        }else{
            editName.setEnabled(true);
            editDescription.setEnabled(true);
            btnCreate.setVisibility(View.VISIBLE);
        }
    }

    void getSession() {
        JsonObject object = new JsonObject();
        object.addProperty("sessionID", id);
        sessionViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETSESSIONFROMID, object)));
    }

    void setImage() {
        ImageRequester imageRequester = new ImageRequester();
        imageRequester.execute(QRCodeHelper.generateBarCode(id), image);
    }

    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(Fragment_Survey.class, "getSocket: " + socketEventModel.getPayloadAsString());
        String toJSONString = socketEventModel.getPayloadAsString();
        try {
            JSONObject jsonObject = new JSONObject(toJSONString);
            if(jsonObject.getString("type").equals("Refresh")){
                System.out.println("Refresh");
                getSession();
            }
            if(jsonObject.getString("result").equals("Error")){
                JSONObject errorObj = jsonObject.getJSONObject("error").getJSONObject("errors").getJSONObject("name");
                toast = Toast.makeText(this, errorObj.getString("message"), Toast.LENGTH_LONG);
                toast.show();
            }
            if (jsonObject.has("session") && jsonObject.getString("result").equals("Session")) {
                System.out.println("Session");
                session = SurveyHelper.getSessionFromJSONOBject(jsonObject.getJSONObject("session"));
                editName.setText(session.name);
                editDescription.setText(session.description);
                participants = session.participants;
                if(session.owner.equals(PreferenceUtil.getDeviceId())){
                    isOwner = true;
                    getIsOwner();
                }
                recyclerView.setAdapter(new SessionAdapter(participants, isOwner));
                setImage();
            }
            if(jsonObject.has("type") && jsonObject.getString("type").equals("Answer") && jsonObject.has("result")){
                id = jsonObject.getString("result");
                toast = Toast.makeText(this, "Session created", Toast.LENGTH_LONG);
                toast.show();
                getSession();
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    };
}
