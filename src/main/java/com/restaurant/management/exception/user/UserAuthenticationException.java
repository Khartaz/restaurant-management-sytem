package com.restaurant.management.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserAuthenticationException extends AuthenticationException {

    public UserAuthenticationException(String exception) {
        super(exception);
    }
}
