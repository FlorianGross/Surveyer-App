package com.floriang.surveyer.ui.surveycreate;

import androidx.lifecycle.ViewModel;

import com.floriang.surveyer.backend.SocketLiveData;

public class SurveyViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;

    public SurveyViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
