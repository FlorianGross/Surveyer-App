package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RefreshSessionJSON {
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Refresh")
    public String refresh;
    @JsonProperty("Result")
    public List<SessionJSON> result;

    public RefreshSessionJSON(String type, String refresh, List<SessionJSON> result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public String getRefresh() {
        return refresh;
    }

    public List<SessionJSON> getResult() {
        return result;
    }
}
