package com.restaurant.management.exception;

public enum  ExceptionMessage {
    INVALID_PHONE_NUMBER("Incorrect phone number. " +
            "Example:" +
            " +1 1234567890123 or " +
            " +12 123456789 or " +
            " +123 123456 ");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param errorMessage the message to set
     */
    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
