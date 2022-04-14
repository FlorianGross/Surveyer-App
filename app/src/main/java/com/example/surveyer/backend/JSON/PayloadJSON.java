package com.example.surveyer.backend.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadJSON {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Refresh")
    private String refresh;
    @JsonProperty("Result")
    private Object result;

    public PayloadJSON(String type, String refresh, Object result) {
        this.type = type;
        this.refresh = refresh;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
