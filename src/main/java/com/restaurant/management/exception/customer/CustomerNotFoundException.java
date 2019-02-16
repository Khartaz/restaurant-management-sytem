package com.restaurant.management.exception.customer;

import com.restaurant.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(String exception) {
        super(exception);
    }
}
