package com.example.surveyer.backend;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.surveyer.App;
import com.example.surveyer.R;
import com.example.surveyer.backend.Util.Constants;
import com.example.surveyer.backend.Util.DebugUtil;
import com.example.surveyer.backend.Util.PreferenceUtil;
import com.example.surveyer.backend.models.pojo.SocketEventModel;

import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SocketLiveData extends LiveData<SocketEventModel> {
    private static final SocketLiveData instance = new SocketLiveData();
    private static WebSocket webSocket;
    private static final AtomicBoolean disconnected = new AtomicBoolean(true);

    private SocketLiveData() {
    }

    public static SocketLiveData get() {
        return instance;
    }

    @Override
    protected synchronized void onActive() {
        super.onActive();
        connect();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super SocketEventModel> observer) {
        super.observe(owner, observer);
        DebugUtil.debug(SocketLiveData.class, "Observing");
        connect();
    }

    /**
     * Connect to the socket
     */
    public synchronized void connect() {
        try {
            DebugUtil.debug(SocketLiveData.class, "Attempting to connect");
            if (disconnected.compareAndSet(true, false)) {
                DebugUtil.debug(SocketLiveData.class, "Connecting...");
                String socketUrl = String.format("%s?%s", Constants.getSocketUrl(), String.format("deviceId=%s", PreferenceUtil.getDeviceId()));
                DebugUtil.debug(SocketLiveData.class, "Socket url: " + socketUrl);
                Request request = new Request.Builder().url(socketUrl)
                        .addHeader("deviceId", PreferenceUtil.getDeviceId()).build();
                webSocket = App.getOkHttpClient().newWebSocket(request, webSocketListener);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendEvent(SocketEventModel eventModel) {
        if (webSocket == null)return;
        setData(eventModel);
        webSocket.send(eventModel.toString());
    }

    public void setData(SocketEventModel socketEventModel) {
        if (socketEventModel == null || TextUtils.isEmpty(socketEventModel.getEvent())) return;
        postValue(socketEventModel);
    }

    private final WebSocketListener webSocketListener = new WebSocketListener() {

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
            disconnected.set(false);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            handleEvent(text);
        }

        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosed(webSocket, code, reason);
            postValue(new SocketEventModel(SocketEventModel.EVENT_OFFLINE, App.getContext().getString(R.string.socket_offline_message))
                    .setType(SocketEventModel.TYPE_INCOMING));
            disconnected.set(true);
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            disconnected.set(true);
            int code = response != null ? response.code() : 400;
            @Nullable String message = response != null ? response.message() : t.getMessage();
            DebugUtil.debug(SocketLiveData.class, String.format("On Failure. Code: %s, message: %s", code, message));
            postValue(new SocketEventModel(SocketEventModel.EVENT_ERROR, code == 401? message : App.getContext().getString(R.string.socket_connection_error_message))
                    .setType(SocketEventModel.TYPE_INCOMING));
            if (code == 400 && message != null && !message.contains("closed")) {
                SocketReconnectionScheduler.schedule();
            }
        }
    };

    private synchronized void handleEvent(String message){
        try {
            SocketEventModel eventModel = SocketEventModel.fromJson(message, SocketEventModel.class)
                    .setType(SocketEventModel.TYPE_INCOMING);
            DebugUtil.debug(SocketLiveData.class, "Handling event: "+message);
            if (TextUtils.isEmpty(eventModel.getEvent()))
                throw new Exception("Invalid event model");
            processEvent(eventModel);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private synchronized void processEvent(SocketEventModel eventModel) {
        DebugUtil.debug(SocketLiveData.class, "Processing event: "+eventModel.toString());
        postValue(eventModel);
    }
    @Override
    protected void onInactive() {
        super.onInactive();
        disconnect();
        DebugUtil.debug(SocketLiveData.class, "Inactive. Has observers observers? "+hasActiveObservers());
    }

    public boolean isDisconnected() {
        return disconnected.get();
    }

    public void disconnect() {
        if (!hasActiveObservers())
            if (webSocket != null)
                webSocket.close(1000, "Done using");
    }

}
