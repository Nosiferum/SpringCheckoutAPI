package com.dogukan.springcheckoutapi.response;

public class SuccessfulResponse {
    private boolean result;
    private String message;

    public SuccessfulResponse() {

    }

    public SuccessfulResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
