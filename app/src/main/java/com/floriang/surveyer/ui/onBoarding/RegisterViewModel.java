package com.floriang.surveyer.ui.onBoarding;

import androidx.lifecycle.ViewModel;

import com.floriang.surveyer.backend.SocketLiveData;

public class RegisterViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;

    public RegisterViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }

}
