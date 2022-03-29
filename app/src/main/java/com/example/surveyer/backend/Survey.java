package com.example.surveyer.backend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Survey {
    @PrimaryKey(autoGenerate = true)
    public int surveyId;
    @ColumnInfo(name = "survey_name")
    public String surveyName;
    @ColumnInfo(name = "survey_description")
    public String surveyDescription;
    @ColumnInfo(name = "survey_owner")
    public String surveyOwner;
    @ColumnInfo(name = "survey_approved")
    public int surveyApproved;
    @ColumnInfo(name = "survey_deny")
    public int surveyDeny;
    @ColumnInfo(name = "survey_skip")
    public int surveySkip;
    @ColumnInfo(name = "survey_finished")
    public boolean surveyFinished;
    @ColumnInfo(name = "survey_sessionID")
    public String surveySessionID;
    @ColumnInfo(name = "survey_participants")
    public String[] surveyParticipants;

    public Survey(String surveyName, String surveyDescription, String surveyOwner, int surveyApproved, int surveyDeny, int surveySkip, boolean surveyFinished, String surveySessionID, String[] surveyParticipants) {
        this.surveyName = surveyName;
        this.surveyDescription = surveyDescription;
        this.surveyOwner = surveyOwner;
        this.surveyApproved = surveyApproved;
        this.surveyDeny = surveyDeny;
        this.surveySkip = surveySkip;
        this.surveyFinished = surveyFinished;
        this.surveySessionID = surveySessionID;
        this.surveyParticipants = surveyParticipants;
    }
    
    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    public String getSurveyOwner() {
        return surveyOwner;
    }

    public void setSurveyOwner(String surveyOwner) {
        this.surveyOwner = surveyOwner;
    }

    public int getSurveyApproved() {
        return surveyApproved;
    }

    public void setSurveyApproved(int surveyApproved) {
        this.surveyApproved = surveyApproved;
    }

    public int getSurveyDeny() {
        return surveyDeny;
    }

    public void setSurveyDeny(int surveyDeny) {
        this.surveyDeny = surveyDeny;
    }

    public int getSurveySkip() {
        return surveySkip;
    }

    public void setSurveySkip(int surveySkip) {
        this.surveySkip = surveySkip;
    }

    public boolean isSurveyFinished() {
        return surveyFinished;
    }

    public void setSurveyFinished(boolean surveyFinished) {
        this.surveyFinished = surveyFinished;
    }

    public String getSurveySessionID() {
        return surveySessionID;
    }

    public void setSurveySessionID(String surveySessionID) {
        this.surveySessionID = surveySessionID;
    }

    public String[] getSurveyParticipants() {
        return surveyParticipants;
    }

    public void setSurveyParticipants(String[] surveyParticipants) {
        this.surveyParticipants = surveyParticipants;
    }
}
