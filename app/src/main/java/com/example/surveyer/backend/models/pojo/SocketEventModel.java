package com.example.surveyer.backend.models.pojo;

import com.example.surveyer.App;
import com.example.surveyer.backend.models.BaseModel;

public class SocketEventModel extends BaseModel {
    public static final String EVENT_ONLINE = "online";
    public static final String EVENT_OFFLINE = "offline";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_MESSAGE = "message";

    public static final int TYPE_OUTGOING = 0;
    public static final int TYPE_INCOMING = 1;

    private final String event;
    private int type;
    private final Object payload;

    public SocketEventModel(String event, Object payload) {
        this.event = event;
        this.payload = payload;
    }

    public SocketEventModel setType(int type) {
        this.type = type;
        return this;
    }

    public String getEvent() {
        return event;
    }

    public Object getPayload() {
        return payload;
    }

    public String getPayloadAsString() {
        String payloadJson = App.getGson().toJson(payload);
        if (payloadJson.startsWith("{") && payloadJson.endsWith("}"))
            return payloadJson;
        return payload.toString();
    }

    public <T>T payloadToJson(Class<T>typeOf) {
        return App.getGson().fromJson(getPayloadAsString(), typeOf);
    }

    public int getType() {
        return type;
    }
}