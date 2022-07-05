package com.floriang.surveyer.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.floriang.surveyer.R;
import com.floriang.surveyer.backend.helper.SurveyHelper;
import com.floriang.surveyer.backend.json.PayloadJSON;
import com.floriang.surveyer.backend.json.SessionJSON;
import com.floriang.surveyer.backend.json.SurveyJSON;
import com.floriang.surveyer.backend.models.pojo.SocketEventModel;
import com.floriang.surveyer.backend.util.DebugUtil;
import com.floriang.surveyer.backend.util.PreferenceUtil;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Survey extends Fragment {
    Spinner session;
    final SurveyJSON survey = new SurveyJSON();
    SurveyViewModel surveyViewModel;
    ArrayAdapter<String> sessionAdapter;
    Button btn;
    EditText name, description;
    SwitchCompat anonymous, enthaltung;
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
        enthaltung = view.findViewById(R.id.enthaltungSwitch);
        session = view.findViewById(R.id.sessionOfSurveySpinner);
        name = view.findViewById(R.id.nameOfSurveyEdit);
        description = view.findViewById(R.id.descriptionOfSurveyEdit);
        anonymous = view.findViewById(R.id.anonymousSwitch);
        surveyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(SurveyViewModel.class);
        surveyViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        surveyViewModel.getSocketLiveData().connect();
        setAdapter();
        btn.setOnClickListener(v -> {
            survey.setSurveyName(name.getText().toString());
            survey.setSurveyDescription(description.getText().toString());
            survey.setCreator(PreferenceUtil.getDeviceId());
            survey.anonymous = anonymous.isChecked();
            survey.allowEnthaltung = enthaltung.isChecked();
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
            if(sessionJSON.name != null) {
                namesOfSessions.add(sessionJSON.name);
            }else{
                namesOfSessions.add(sessionJSON.id);
            }
        }
        System.out.println(namesOfSessions.size());
        sessionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesOfSessions);
        session.setAdapter(sessionAdapter);
        session.setOnItemSelectedListener(sessionListener);
    }
    private void updateAdapter(){
        namesOfSessions.clear();
        for (SessionJSON sessionJSON : sessions) {
            if(sessionJSON.name != null) {
                namesOfSessions.add(sessionJSON.name);
            }else{
                namesOfSessions.add(sessionJSON.id);
            }
        }
        if(sessionAdapter != null){
            sessionAdapter.notifyDataSetChanged();
        }
    }

    void getAllSessions() {
        JsonObject object = new JsonObject();
        object.addProperty("uid", PreferenceUtil.getDeviceId());
        surveyViewModel.getSocketLiveData().sendEvent(new SocketEventModel(SocketEventModel.EVENT_MESSAGE, new PayloadJSON(PayloadJSON.TYPE_GETALLSESSIONSNAMES, object)));
    }

    final AdapterView.OnItemSelectedListener sessionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String item = sessions.get(i).id;
            DebugUtil.debug(Fragment_Survey.class, "Selected: " + item);
            survey.surveySession = item;
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
            if(jsonObject.getString("type").equals("Refresh")){
                getAllSessions();
            }
            if(jsonObject.getString("result").equals("Error")){
                JSONObject errorObj = jsonObject.getJSONObject("error").getJSONObject("errors").getJSONObject("surveyName");
                System.out.println(errorObj.getString("message"));
                Toast.makeText(requireActivity(), errorObj.getString("message"), Toast.LENGTH_LONG).show();

            }
            if(jsonObject.has("sessions") && jsonObject.getJSONArray("sessions").length() > 0){
                JSONArray jsonArray = jsonObject.getJSONArray("sessions");
                sessions = SurveyHelper.getSessionListFromJSONArray(jsonArray);
                updateAdapter();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    };
}