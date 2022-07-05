package com.floriang.surveyer.backend.models.pojo;

import com.floriang.surveyer.App;
import com.floriang.surveyer.backend.models.BaseModel;

public class SocketAnswerModel extends BaseModel {
    private String type;
    private Object result;
    private Object error;
    private String uid;
    private Object events;

    public SocketAnswerModel(String type, Object result) {
        this.type = type;
        this.result = result;
    }

    public SocketAnswerModel(String type, Object result, Object error) {
        this.type = type;
        this.result = result;
        this.error = error;
    }

    public SocketAnswerModel(String type, Object result, String uid) {
        this.type = type;
        this.result = result;
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResultAsString() {
        String payloadJson = App.getGson().toJson(result);
        if (payloadJson.startsWith("{") && payloadJson.endsWith("}"))
            return payloadJson;
        return result.toString();
    }

    public <T>T payloadToJson(Class<T>typeOf) {
        return App.getGson().fromJson(getResultAsString(), typeOf);
    }
}
