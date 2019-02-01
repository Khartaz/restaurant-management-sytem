package com.restaurant.management.exception.user;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {

    public UserAuthenticationException(String exception) {
        super(exception);
    }
}
