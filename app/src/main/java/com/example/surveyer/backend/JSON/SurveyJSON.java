package com.example.surveyer.backend.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurveyJSON {
    @JsonProperty("_id")
    public final String surveyID;
    @JsonProperty("surveySession")
    public final String surveySession;
    @JsonProperty("creator")
    public final String creator;
    @JsonProperty("surveyDescription")
    public final String surveyDescription;
    @JsonProperty("surveyOpened")
    public final boolean surveyOpened;
    @JsonProperty("surveyName")
    public final String surveyName;
    @JsonProperty("surveyApprove")
    public final int surveyApprove;
    @JsonProperty("surveyDeny")
    public final int surveyDeny;
    @JsonProperty("surveyNotParicipate")
    public final int surveyNotParicipate;
    @JsonProperty("participants")
    public final String[] participants;
    @JsonProperty("__v")
    public final int version;

    @JsonCreator
    public SurveyJSON(@JsonProperty("_id") String surveyID, @JsonProperty("surveySession") String surveySession, @JsonProperty("creator") String creator,
                      @JsonProperty("surveyDescription") String surveyDescription, @JsonProperty("surveyOpened") boolean surveyOpened,
                      @JsonProperty("surveyName") String surveyName, @JsonProperty("surveyApprove") int surveyApprove,
                      @JsonProperty("surveyDeny") int surveyDeny, @JsonProperty("surveyNotParicipate") int surveyNotParicipate,
                      @JsonProperty("participants") String[] participants, @JsonProperty("__v") int version) {
        this.surveyID = surveyID;
        this.surveySession = surveySession;
        this.creator = creator;
        this.surveyDescription = surveyDescription;
        this.surveyOpened = surveyOpened;
        this.surveyName = surveyName;
        this.surveyApprove = surveyApprove;
        this.surveyDeny = surveyDeny;
        this.surveyNotParicipate = surveyNotParicipate;
        this.participants = participants;
        this.version = version;
    }
}
