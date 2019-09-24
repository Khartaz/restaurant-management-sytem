package com.restaurant.management.exception.ecommerce.customer;

import com.restaurant.management.exception.ExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerExistsException extends ExistsException {
    public CustomerExistsException(String exception) {
        super(exception);
    }
}
