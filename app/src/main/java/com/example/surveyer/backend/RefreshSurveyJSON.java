package com.example.surveyer.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RefreshSurveyJSON {
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Refresh")
    public String refresh;
    @JsonProperty("Result")
    public List<SurveyJSON> result;
}
