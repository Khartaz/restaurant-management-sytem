package com.restaurant.management.exception.user;

public enum UserMessages {

    REGISTER_SUCCESS("User registered successfully"),
    EMAIL_TAKEN("Email is already taken"),
    EMAIL_AVAILABLE("Email available"),
    USERNAME_TAKEN("Username is already taken"),
    UNAUTHENTICATED("Unauthenticated"),
    ROLE_NOT_SET("Role not set."),
    ACCOUNT_DELETED("Account deleted"),
    PASSWORDS_EQUALS("Password and confirmed password must be this same"),
    USER_NOT_FOUND("User not found with username or email : "),
    ID_NOT_FOUND("User not found with id: "),
    UNIQUE_ID_NOT_FOUND("User not found with unique id : "),
    ACCOUNT_DISABLED("Account is disabled. Please verify email first.");


    private String message;

    UserMessages(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param errorMessage the message to set
     */
    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
