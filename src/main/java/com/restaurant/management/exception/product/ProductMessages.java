package com.restaurant.management.exception.product;

public enum  ProductMessages {

    PRODUCT_ID_NOT_FOUND("Product not found with provided id. Register it first. "),
    PRODUCT_ALREADY_IN_CART("Product with provided id is already in cart"),
    PRODUCT_NAME_NOT_FOUND("Product not found with provided name. "),
    PRODUCT_UNIQUE_ID_NOT_FOUND("Product not found with provided unique id. Register it first. "),
    ARCHIVED("Product has been moved to the archive. "),
    PRODUCT_DELETED("Product deleted"),
    PRODUCTS_DELETED("Products deleted"),
    PRODUCT_NAME_AVAILABLE("Product name available"),
    PRODUCT_NAME_EXISTS("Product with provided name already exists. ");


    private String errorMessage;

    ProductMessages(String errorMessage) {
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
