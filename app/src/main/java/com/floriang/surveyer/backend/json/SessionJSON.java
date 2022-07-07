package com.floriang.surveyer.backend.json;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionJSON {
    @JsonProperty("_id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("owner")
    public String owner;
    @JsonProperty("participants")
    public String[] participants;
    @JsonProperty("surveyArray")
    public SurveyJSON[] surveyArray;
    @JsonProperty("isActive")
    public boolean isActive;
    @JsonProperty("__v")
    public int version;

    public SessionJSON(){

    }

    @NonNull
    @Override
    public String toString() {
        return "SessionJSON{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", isActive=" + isActive +
                ", version=" + version +
                '}';
    }
}
