package com.example.surveyer.backend.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RefreshSessionJSON {
    @JsonProperty("Type")
    final public String type;
    @JsonProperty("Refresh")
    final public String refresh;
    @JsonProperty("Result")
    final public SessionJSON[] result;

    @JsonCreator
    public RefreshSessionJSON(@JsonProperty("Type") String type,
                              @JsonProperty("Refresh") String refresh,
                              @JsonProperty("Result") SessionJSON[] result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }
}
