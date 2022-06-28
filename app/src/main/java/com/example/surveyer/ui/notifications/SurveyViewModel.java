package com.example.surveyer.ui.notifications;

import androidx.lifecycle.ViewModel;

import com.example.surveyer.backend.SocketLiveData;

public class SurveyViewModel extends ViewModel {
    private SocketLiveData socketLiveData;

    public SurveyViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
