package com.example.surveyer.backend.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshSurveyJSON {
    @JsonProperty("_id")
    public String id;
    @JsonProperty("Type")
    public final String type;
    @JsonProperty("Refresh")
    public final String refresh;
    @JsonProperty("Result")
    public final SurveyJSON[] result;
    @JsonProperty("__v")
    public int version;


    @JsonCreator
    public RefreshSurveyJSON(@JsonProperty("Type") String type,
                             @JsonProperty("Refresh") String refresh,
                             @JsonProperty("Result") SurveyJSON[] result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }


}
