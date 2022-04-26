package com.example.surveyer.ui.survey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.surveyer.backend.json.SurveyJSON;
import com.example.surveyer.backend.SocketLiveData;

public class SurveyViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;
    MutableLiveData<SurveyJSON> survey;

    public SurveyViewModel() {
        socketLiveData = SocketLiveData.get();
       // survey = socketLiveData.getSurvey();
    }

    public void setSurvey(SurveyJSON survey) {
        this.survey.setValue(survey);
    }
    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
