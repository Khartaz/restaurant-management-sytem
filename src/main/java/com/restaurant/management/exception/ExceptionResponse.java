package com.restaurant.management.exception;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String uri;
    private List<String> errorDetails;

    public ExceptionResponse(Date timestamp, String message, String uri) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.uri = uri;
    }

    public ExceptionResponse(Date timestamp, String message, String uri, List<String> errorDetails) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.uri = uri;
        this.errorDetails = errorDetails;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUri() {
        return uri;
    }

    public List<String> getErrorDetails() {
        return errorDetails;
    }
}