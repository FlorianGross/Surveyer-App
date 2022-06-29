package com.example.surveyer.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.surveyer.R;
import com.example.surveyer.backend.helper.SurveyHelper;
import com.example.surveyer.backend.json.PayloadJSON;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.backend.util.PreferenceUtil;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Survey extends Fragment {
    Spinner type, session;
    SurveyJSON survey = new SurveyJSON();
    SurveyViewModel surveyViewModel;
    ArrayAdapter<String> sessionAdapter;
    Button btn;
    EditText name, description;
    Switch anonymous;
    ArrayList<String> namesOfSessions = new ArrayList<>();
    ArrayList<SessionJSON> sessions = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.surveyCreateButton);
        type = view.findViewById(R.id.typeOFSurveySpinner);
        session = view.findViewById(R.id.sessionOfSurveySpinner);
        name = view.findViewById(R.id.nameOfSurveyEdit);
        description = view.findViewById(R.id.descriptionOfSurveyEdit);
        anonymous = view.findViewById(R.id.anonymousSwitch);
        surveyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(SurveyViewModel.class);
        surveyViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        surveyViewModel.getSocketLiveData().connect();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.survey_type, android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(listener);
        setAdapter();
        btn.setOnClickListener(v -> {
            survey.setSurveySession(session.getSelectedItem().toString());
            survey.setSurveyName(name.getText().toString());
            survey.setSurveyDescription(description.getText().toString());
            survey.setCreator(PreferenceUtil.getDeviceId());
            survey.anonymous = anonymous.isChecked();
            survey.surveyOpened = true;
            try {
                surveyViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_CREATESURVEY, survey)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        getAllSessions();
    }

    private void setAdapter() {
        namesOfSessions = new ArrayList<>();
        for (SessionJSON sessionJSON : sessions) {
            namesOfSessions.add(sessionJSON.id);
        }
        sessionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesOfSessions);
        session.setAdapter(sessionAdapter);
        session.setOnItemSelectedListener(sessionListener);
    }
    private void updateAdapter(){
        namesOfSessions.clear();
        for (SessionJSON sessionJSON : sessions) {
            namesOfSessions.add(sessionJSON.id);
        }
        if(sessionAdapter != null){
            sessionAdapter.notifyDataSetChanged();
        }
    }

    void getAllSessions() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        surveyViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSESSIONS, object)));
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String item = adapterView.getItemAtPosition(i).toString();
            switch (item) {
                case "Ja/Nein": {}
                break;
                case "Ja/Nein/Enthaltung": {}
                break;
                case "Dafür/Dagegen/Enthaltung": {}
                break;
                case "Dafür/Dagegen": {}
                break;
                default:{
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    AdapterView.OnItemSelectedListener sessionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String item = adapterView.getItemAtPosition(i).toString();
            survey.setSurveySession(item);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private final Observer<SocketEventModel> socketEventModelObserver = socketEventModel -> {
        DebugUtil.debug(Fragment_Survey.class, "getSocket: " + socketEventModel.toString());
        String toJSONString = socketEventModel.getPayloadAsString();
        try {
            JSONObject jsonObject = new JSONObject(toJSONString);
            if(jsonObject.has("events") && jsonObject.getJSONArray("events").length() > 0){
                ArrayList<SessionJSON> newSession = SurveyHelper.getSessionListFromObject(jsonObject);
                sessions = newSession;
                updateAdapter();
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    };
}