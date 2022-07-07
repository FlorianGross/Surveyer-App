package com.floriang.surveyer.backend.json;

import com.floriang.surveyer.backend.models.BaseModel;
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
    public static final String TYPE_JOINSESSION = "joinSession";
    public static final String TYPE_GETALLSESSIONSANDSUREYS = "getAllSessionsAndSurveys";
    public static final String TYPE_VOTE = "voteForSurvey";
    public static final String TYPE_GETALLSESSIONSNAMES = "getAllSessionsNames";
    public static final String TYPE_LEAVESESSION = "leaveSession";
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Result")
    private Object result;
    @JsonProperty("Error")
    private String error;


    public PayloadJSON(String type, Object result) {
        this.type = type;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
