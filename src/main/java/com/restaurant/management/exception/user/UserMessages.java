package com.restaurant.management.exception.user;

public enum UserMessages {

    REGISTER_SUCCESS("UserResponse registered successfully"),
    EMAIL_TAKEN("Email is already taken"),
    EMAIL_AVAILABLE("Email available"),
    UNAUTHENTICATED("Unauthenticated"),
    ACCESS_DENIED("Access denied"),
    ROLE_NOT_SET("Role not set."),
    ACCOUNT_DELETED("Account deleted"),
    ACCOUNTS_DELETED("Accounts deleted"),
    USER_NOT_FOUND("UserResponse not found with email : "),
    ID_NOT_FOUND("UserResponse not found with id: "),
    UNIQUE_ID_NOT_FOUND("UserResponse not found with unique id : "),
    INCORRECT_CREDENTIALS("Incorrect credentials"),
    PASSWORD_CHANGED("Password changed"),
    DIFFERENT_PASSWORD("Password must be different then old one"),
    PASSWORDS_EQUALS("Password and confirmed password must be this same"),
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
