package com.restaurant.management.exception.company;

public enum CompanyMessages {
    RESTAURANT_NOT_FOUND("Company not found with provided id.");

    private String errorMessage;

    CompanyMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
