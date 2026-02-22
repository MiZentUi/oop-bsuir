package com.forecast.shared.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    public StatusResponse() {
    }

    public StatusResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
