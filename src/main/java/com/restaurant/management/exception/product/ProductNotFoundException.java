package com.restaurant.management.exception.product;

import com.restaurant.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String exception) {
        super(exception);
    }
}
