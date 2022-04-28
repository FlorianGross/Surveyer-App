package com.example.surveyer.ui.onboarding;

import androidx.lifecycle.ViewModel;

import com.example.surveyer.backend.SocketLiveData;

public class LoginViewModel extends ViewModel {
    private SocketLiveData socketLiveData;

    public LoginViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
