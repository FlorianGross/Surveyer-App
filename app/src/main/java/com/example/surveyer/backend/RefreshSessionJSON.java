package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RefreshSessionJSON {
    @JsonProperty("Type")
    public final String type;
    @JsonProperty("Refresh")
    public final String refresh;
    @JsonProperty("Result")
    public final String result;

    public RefreshSessionJSON(String type, String refresh, String result) {
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

    public String getResult() {
        return result;
    }
}
