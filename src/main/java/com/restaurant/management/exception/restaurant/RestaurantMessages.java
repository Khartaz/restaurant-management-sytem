package com.restaurant.management.exception.restaurant;

public enum RestaurantMessages {
    RESTAURANT_NOT_FOUND("Restaurant not found with provided id.");

    private String errorMessage;

    RestaurantMessages(String errorMessage) {
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
