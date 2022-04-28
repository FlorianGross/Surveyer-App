package com.example.surveyer.backend.json;

import com.example.surveyer.backend.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerJSON extends BaseModel {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Result")
    private Object result;
    @JsonProperty("Error")
    private String error;

    public AnswerJSON(String type, Object result) {
        this.type = type;
        this.result = result;
    }

    public AnswerJSON(String type, Object result, String error) {
        this.type = type;
        this.result = result;
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
