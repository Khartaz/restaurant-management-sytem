package com.restaurant.management.exception.customer;

public enum CustomerMessages {
    CUSTOMER_NOT_REGISTER("Please register customer first. "),
    CUSTOMER_PHONE_EXISTS("Customer with provided phone number already exists. "),
    ID_NOT_FOUND("Customer with provided id not found. "),
    CUSTOMER_DELETED("Customer deleted"),
    CUSTOMER_EMAIL_EXISTS("Customer with provided email already exists. ");

    private String errorMessage;

    CustomerMessages(String errorMessage) {
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
