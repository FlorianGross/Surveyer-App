package com.example.surveyer.backend.json;

import com.example.surveyer.backend.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadJSON extends BaseModel {
    public static final String TYPE_REGISTER = "registerUser";
    public static final String TYPE_LOGIN = "loginUser";
    public static final String TYPE_GETALLSESSIONS = "getAllSessions";
    public static final String TYPE_GETALLSURVEYS = "getAllSurveys";
    public static final String TYPE_CREATESURVEY = "createSurvey";
    public static final String TYPE_GETSURVEYFROMID = "getSurveyFromID";
    public static final String TYPE_GETSESSIONFROMID = "getSessionFromID";
    public static final String TYPE_UPDATESESSION = "updateSession";
    public static final String TYPE_CREATESESSION = "createSession";
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Refresh")
    private String refresh;
    @JsonProperty("Result")
    private Object result;
    @JsonProperty("Error")
    private String error;
    @JsonProperty("events")
    private String events;

    public PayloadJSON(String type, String refresh, Object result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }

    public PayloadJSON(String type, Object result) {
        this.type = type;
        this.result = result;
    }

    public PayloadJSON(String type, Object result, String events) {
        this.type = type;
        this.result = result;
        this.events = events;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
