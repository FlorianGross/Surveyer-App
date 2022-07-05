package com.floriang.surveyer.ui.session;

import androidx.lifecycle.ViewModel;

import com.floriang.surveyer.backend.SocketLiveData;

public class SessionViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;

    public SessionViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }
}
