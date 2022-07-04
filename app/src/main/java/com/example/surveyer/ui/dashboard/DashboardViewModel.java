package com.example.surveyer.ui.dashboard;

import androidx.lifecycle.ViewModel;

import com.example.surveyer.backend.SocketLiveData;

public class DashboardViewModel extends ViewModel{
    private final SocketLiveData socketLiveData;

    public DashboardViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }

}
