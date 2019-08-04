package com.restaurant.management.web.response;

public final class ApiResponse {
    private Boolean success;
    private Integer status;
    private String message;
    private Object result;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Integer status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}