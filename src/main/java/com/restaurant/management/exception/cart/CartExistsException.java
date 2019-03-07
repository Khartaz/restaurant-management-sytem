package com.restaurant.management.exception.cart;

import com.restaurant.management.exception.ExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartExistsException extends ExistsException {
    public CartExistsException(String exception) {
        super(exception);
    }
}
