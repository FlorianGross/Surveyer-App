package com.floriang.surveyer.backend.models.pojo;

import com.floriang.surveyer.App;
import com.floriang.surveyer.backend.models.BaseModel;

public class SocketEventModel extends BaseModel {
    public static final String EVENT_ONLINE = "online";
    public static final String EVENT_OFFLINE = "offline";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_MESSAGE = "message";
    public static final String EVENT_REFRESH = "refresh";
    public static final String LOC_DASHBOARD = "dashboard";
    public static final String LOC_SESSION = "session";
    public static final String LOC_SURVEY = "survey";
    public static final String LOC_VOTE = "vote";
    public static final String LOC_LOGIN = "login";
    public static final String LOC_REGISTER = "register";

    public static final int TYPE_OUTGOING = 0;
    public static final int TYPE_INCOMING = 1;
    public static final String LOC_HOME = "home";

    private final String event;
    private int type;
    private final Object payload;
    private final String location;

    public SocketEventModel(String event, Object payload, String location) {
        this.event = event;
        this.payload = payload;
        this.location = location;
    }

    public SocketEventModel setType(int type) {
        this.type = type;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public String getEvent() {
        return event;
    }

    public Object getPayload() {
        System.out.println("Payload: " + payload);
        return payload;
    }

    public SocketAnswerModel getAnswer() {
        SocketAnswerModel answer = null;
        if (payload instanceof SocketAnswerModel) {
            answer = (SocketAnswerModel) payload;
        }
        return answer;
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