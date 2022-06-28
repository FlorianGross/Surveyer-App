package com.example.surveyer.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.surveyer.R;
import com.example.surveyer.backend.json.SessionJSON;
import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.models.pojo.SocketEventModel;
import com.example.surveyer.backend.util.DebugUtil;
import com.example.surveyer.ui.Onboarding.Login;
import com.example.surveyer.ui.home.HomeViewModel;

import java.util.ArrayList;

public class Fragment_Survey extends Fragment {
    Spinner type, session;
    SurveyJSON survey = new SurveyJSON();
    SurveyViewModel surveyViewModel;
    SessionJSON[] sessions = {
            new SessionJSON("1", "1", "1", "1", null, null, true, 0),
            new SessionJSON("2", "2", "2", "2", null, null, true, 0),

    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surveyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(SurveyViewModel.class);
        surveyViewModel.getSocketLiveData().observe(requireActivity(), socketEventModelObserver);
        surveyViewModel.getSocketLiveData().connect();
        type = view.findViewById(R.id.typeOFSurveySpinner);
        session = view.findViewById(R.id.sessionOfSurveySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.survey_type, android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(listener);
        ArrayList<String> namesOfSessions = new ArrayList<>();
        for (SessionJSON sessionJSON : sessions) {
            namesOfSessions.add(sessionJSON.name);
        }
        ArrayAdapter<String> sessionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesOfSessions);
        session.setAdapter(sessionAdapter);
        session.setOnItemSelectedListener(sessionListener);
    }
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String item = adapterView.getItemAtPosition(i).toString();
            switch (item) {
                case "Ja/Nein": {

                }
                break;
                case "Ja/Nein/Enthaltung": {

                }
                break;
                case "Dafür/Dagegen/Enthaltung": {

                }
                break;
                case "Dafür/Dagegen": {

                }
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
        DebugUtil.debug(Login.class, "New Socket event: " + socketEventModel.toString());

    };
}