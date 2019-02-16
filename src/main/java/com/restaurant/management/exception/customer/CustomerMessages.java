package com.restaurant.management.exception.customer;

public enum  CustomerMessages {
    CUSTOMER_NOT_REGISTER("Please register customer first");

    private String errorMessage;

    CustomerMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
