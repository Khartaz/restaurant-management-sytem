package com.restaurant.management.exception.cart;

public enum  CartMessages {
    CART_NOT_FOUND("First add products to cart"),
    CART_DELETED("Cart deleted"),
    CART_UNIQUE_ID_NOT_FOUND("Cart with provided unique id not found. ");

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