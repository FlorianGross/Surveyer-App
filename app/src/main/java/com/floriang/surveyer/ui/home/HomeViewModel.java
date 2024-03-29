package com.floriang.surveyer.ui.home;

import androidx.lifecycle.ViewModel;
import com.floriang.surveyer.backend.SocketLiveData;

public class HomeViewModel extends ViewModel {
    private final SocketLiveData socketLiveData;

    public HomeViewModel() {
        socketLiveData = SocketLiveData.get();
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }

}
