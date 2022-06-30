package com.example.surveyer.backend.json;

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
    @JsonProperty("surveys")
    public String[] surveys;
    @JsonProperty("isActive")
    public boolean isActive;
    @JsonProperty("__v")
    public int version;

    public SessionJSON(@JsonProperty("_id") String id, @JsonProperty("name") String name, @JsonProperty("description") String description ,@JsonProperty("owner") String owner, @JsonProperty("participants") String[] participants, @JsonProperty("surveys") String[] surveys, @JsonProperty("isActive") boolean isActive, @JsonProperty("__v") int version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.participants = participants;
        this.surveys = surveys;
        this.isActive = isActive;
        this.version = version;
    }

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
                ", participants=" + participants +
                ", surveys=" + surveys +
                ", isActive=" + isActive +
                ", version=" + version +
                '}';
    }
}
