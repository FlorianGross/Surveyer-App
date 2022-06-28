package com.example.surveyer.backend.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurveyJSON {
    @JsonProperty("_id")
    public String surveyID;
    @JsonProperty("surveySession")
    public String surveySession;
    @JsonProperty("creator")
    public String creator;
    @JsonProperty("surveyDescription")
    public String surveyDescription;
    @JsonProperty("surveyOpened")
    public boolean surveyOpened;
    @JsonProperty("surveyName")
    public String surveyName;
    @JsonProperty("surveyApprove")
    public int surveyApprove;
    @JsonProperty("surveyDeny")
    public int surveyDeny;
    @JsonProperty("surveyNotParicipate")
    public int surveyNotParicipate;
    @JsonProperty("anonymous")
    public boolean anonymous;
    @JsonProperty("participants")
    public String[] participants;
    @JsonProperty("__v")
    public int version;

    @JsonCreator
    public SurveyJSON(@JsonProperty("_id") String surveyID, @JsonProperty("surveySession") String surveySession, @JsonProperty("creator") String creator,
                      @JsonProperty("surveyDescription") String surveyDescription, @JsonProperty("surveyOpened") boolean surveyOpened,
                      @JsonProperty("surveyName") String surveyName, @JsonProperty("surveyApprove") int surveyApprove,
                      @JsonProperty("surveyDeny") int surveyDeny, @JsonProperty("surveyNotParicipate") int surveyNotParicipate,
                      @JsonProperty("anonymous") boolean anonymous,
                      @JsonProperty("participants") String[] participants, @JsonProperty("__v") int version) {
        this.surveyID = surveyID;
        this.surveySession = surveySession;
        this.creator = creator;
        this.surveyDescription = surveyDescription;
        this.surveyOpened = surveyOpened;
        this.surveyName = surveyName;
        this.surveyApprove = surveyApprove;
        this.surveyDeny = surveyDeny;
        this.anonymous = anonymous;
        this.surveyNotParicipate = surveyNotParicipate;
        this.participants = participants;
        this.version = version;
    }

    public SurveyJSON(){

    }

    public String getSurveyID() {
        return surveyID;
    }

    public String getSurveySession() {
        return surveySession;
    }

    public String getCreator() {
        return creator;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public boolean isSurveyOpened() {
        return surveyOpened;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public int getSurveyApprove() {
        return surveyApprove;
    }

    public int getSurveyDeny() {
        return surveyDeny;
    }

    public int getSurveyNotParicipate() {
        return surveyNotParicipate;
    }

    public String[] getParticipants() {
        return participants;
    }

    public int getVersion() {
        return version;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public void setSurveySession(String surveySession) {
        this.surveySession = surveySession;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    public void setSurveyOpened(boolean surveyOpened) {
        this.surveyOpened = surveyOpened;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public void setSurveyApprove(int surveyApprove) {
        this.surveyApprove = surveyApprove;
    }

    public void setSurveyDeny(int surveyDeny) {
        this.surveyDeny = surveyDeny;
    }

    public void setSurveyNotParicipate(int surveyNotParicipate) {
        this.surveyNotParicipate = surveyNotParicipate;
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
