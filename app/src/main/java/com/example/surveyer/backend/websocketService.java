package com.example.surveyer.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class websocketService extends Service {
    public websocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}