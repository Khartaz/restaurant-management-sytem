package com.restaurant.management.exception.cart;

public enum  CartMessages {
    CART_NOT_FOUND("First add products to cart. "),
    CART_DELETED("Cart deleted. "),
    CART_EXISTS("Cart already register for customer. You can add products. "),
    CART_OPENED_EMPTY("There is no opened customer carts. "),
    CART_CLOSED_EMPTY("There is no closed customer carts. "),
    CART_UNIQUE_ID_NOT_FOUND("Cart with provided unique id not found. ");

    private String errorMessage;

    CartMessages(String errorMessage) {
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