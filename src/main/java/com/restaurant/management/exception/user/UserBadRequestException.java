package com.restaurant.management.exception.user;

import com.restaurant.management.exception.BadRequestException;
import com.restaurant.management.exception.ExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserBadRequestException extends BadRequestException {

    public UserBadRequestException(String exception) {
        super(exception);
    }
}