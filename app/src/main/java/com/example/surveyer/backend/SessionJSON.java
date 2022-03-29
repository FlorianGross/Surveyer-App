package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SessionJSON {
    @JsonProperty("SessionID")
    public String id;
    @JsonProperty("owner")
    public String owner;
    @JsonProperty("participants")
    public List<String> participants;
    @JsonProperty("isActive")
    public boolean isActive;

    public SessionJSON(String id, String owner, List<String> participants, boolean isActive) {
        this.id = id;
        this.owner = owner;
        this.participants = participants;
        this.isActive = isActive;
    }

}
