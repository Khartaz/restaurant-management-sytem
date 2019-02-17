package com.restaurant.management.exception.cart;

import com.restaurant.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartException extends NotFoundException {
    public CartException(String exception) {
        super(exception);
    }
}
