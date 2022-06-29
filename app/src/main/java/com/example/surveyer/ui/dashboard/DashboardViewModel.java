package com.example.surveyer.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.surveyer.backend.SocketLiveData;
import com.example.surveyer.backend.json.SurveyJSON;

public class DashboardViewModel extends ViewModel{
    private final SocketLiveData socketLiveData;

    public DashboardViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }

}
