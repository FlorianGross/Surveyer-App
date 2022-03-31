package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshSurveyJSON {
    @JsonProperty("Type")
    public final String type;
    @JsonProperty("Refresh")
    public final String refresh;
    @JsonProperty("Result")
    public final SurveyJSON[] result;

    @JsonCreator
    public RefreshSurveyJSON(@JsonProperty("Type") String type,
                             @JsonProperty("Refresh") String refresh,
                             @JsonProperty("Result") SurveyJSON[] result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }


}
