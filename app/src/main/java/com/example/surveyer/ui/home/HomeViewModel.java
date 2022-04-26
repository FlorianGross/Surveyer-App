package com.example.surveyer.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.SocketLiveData;

public class HomeViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;
    MutableLiveData<SurveyJSON[]> survey;

    public HomeViewModel() {
        socketLiveData = SocketLiveData.get();
        survey = new MutableLiveData<>();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }

    public MutableLiveData<SurveyJSON[]> getSurvey() {
        return survey;
    }
}
