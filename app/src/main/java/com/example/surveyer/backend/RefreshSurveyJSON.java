package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshSurveyJSON {
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Refresh")
    public String refresh;
    @JsonProperty("Result")
    public String result;
}
