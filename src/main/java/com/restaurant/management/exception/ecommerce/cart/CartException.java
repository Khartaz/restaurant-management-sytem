package com.restaurant.management.exception.ecommerce.cart;

import com.restaurant.management.exception.ExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartException extends ExistsException {
    public CartException(String exception) {
        super(exception);
    }
}
