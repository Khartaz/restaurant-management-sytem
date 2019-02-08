package com.restaurant.management.exception.user;

public enum UserMessages {

    REGISTER_SUCCESS("User registered successfully"),
    EMAIL_TAKEN("Email is already taken"),
    USERNAME_TAKEN("Username is already taken"),
    UNAUTHENTICATED("Unauthenticated"),
    ROLE_NOT_SET("Role not set."),
    PASSWORDS_EQUALS("Password and confirmed password must be this same"),
    USER_NOT_FOUND("User not found with username or email : "),
    UNIQUE_ID_NOT_FOUND("User not found with id : "),
    ACCOUNT_DISABLED("Account is disabled. Please verify email first.");


    private String errorMessage;

    UserMessages(String errorMessage) {
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
