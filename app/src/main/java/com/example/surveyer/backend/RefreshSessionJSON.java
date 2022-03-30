package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RefreshSessionJSON {
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Refresh")
    public String refresh;
    @JsonProperty("Result")
    public SessionJSON[] result;

    @JsonCreator
    public RefreshSessionJSON(@JsonProperty("Type") String type,
                              @JsonProperty("Refresh") String refresh,
                              @JsonProperty("Result") SessionJSON[] result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }
}
