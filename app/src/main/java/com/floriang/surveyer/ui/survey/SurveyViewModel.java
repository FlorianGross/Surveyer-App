package com.floriang.surveyer.ui.survey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.floriang.surveyer.backend.json.SurveyJSON;
import com.floriang.surveyer.backend.SocketLiveData;

public class SurveyViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;
    MutableLiveData<SurveyJSON> survey;

    public SurveyViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public void setSurvey(SurveyJSON survey) {
        this.survey.setValue(survey);
    }
    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
