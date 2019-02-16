package com.restaurant.management.exception.product;

public enum  ProductMessages {

    PRODUCT_NOT_FOUND("Product not found with provided id. Register it first.");


    private String errorMessage;

    ProductMessages(String errorMessage) {
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
