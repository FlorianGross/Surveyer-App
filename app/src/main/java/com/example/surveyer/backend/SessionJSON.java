package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionJSON {
    @JsonProperty("SessionID")
    public final String id;
    @JsonProperty("owner")
    public final String owner;
    @JsonProperty("participants")
    public final String participants;
    @JsonProperty("isActive")
    public final boolean isActive;

    public SessionJSON(String id, String owner, String participants, boolean isActive) {
        this.id = id;
        this.owner = owner;
        this.participants = participants;
        this.isActive = isActive;
    }

}
