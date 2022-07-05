package com.floriang.surveyer.ui.onBoarding;

import androidx.lifecycle.ViewModel;

import com.floriang.surveyer.backend.SocketLiveData;

public class LoginViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;

    public LoginViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
