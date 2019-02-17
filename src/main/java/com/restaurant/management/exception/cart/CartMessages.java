package com.restaurant.management.exception.cart;

public enum  CartMessages {
    CART_NOT_FOUND("First add products to cart");

    private String errorMessage;

    CartMessages(String errorMessage) {
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