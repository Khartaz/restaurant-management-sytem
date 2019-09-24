package com.restaurant.management.exception.ecommerce.cart;

public enum  CartMessages {
    CART_NOT_FOUND("First add products to cart. "),
    CART_DELETED("Session cart deleted. "),
    LINE_ITEM_DELETED("Line item deleted"),
    CART_NOT_REGISTER("Please first open cart for customer. "),
    CART_EXISTS("SessionCart already register for customer. You can add products. "),
    CART_OPENED_EMPTY("There is no opened customer carts. "),
    CART_CLOSED_EMPTY("There is no closed customer carts. "),
    CART_IS_CLOSED("SessionCart is closed cannot be deleted. "),
    PRODUCT_ALREADY_IN_CART("Product is already in cart. "),
    NOT_ENOUGH_AT_CART("Incorrect quantity request. "),
    CUSTOMER_CART_NOT_FOUND2("Cart with provided customer id not found"),
    CUSTOMER_CART_NOT_FOUND("Customer cart with provided id and cart unique id not found"),
    CUSTOMER_SESSION_CART_NOT_FOUND("Customer session cart with provided id not found. "),
    CART_ID_NOT_FOUND("SessionCart with provided id not found. ");

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