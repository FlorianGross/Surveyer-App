package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionJSON {
    @JsonProperty("_id")
    public final String id;
    @JsonProperty("owner")
    public final String owner;
    @JsonProperty("participants")
    public final String[] participants;
    @JsonProperty("isActive")
    public final boolean isActive;
    @JsonProperty("__v")
    public final int version;

    public SessionJSON(@JsonProperty("_id") String id, @JsonProperty("owner") String owner, @JsonProperty("participants") String[] participants, @JsonProperty("isActive") boolean isActive, @JsonProperty("__v") int version) {
        this.id = id;
        this.owner = owner;
        this.participants = participants;
        this.isActive = isActive;
        this.version = version;
    }

}
