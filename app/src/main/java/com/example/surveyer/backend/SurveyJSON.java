package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SurveyJSON {
    @JsonProperty("SurveyID")
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
    @JsonProperty("participants")
    public String participants;
}
