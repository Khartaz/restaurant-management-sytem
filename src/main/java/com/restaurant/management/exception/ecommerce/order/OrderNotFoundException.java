package com.restaurant.management.exception.ecommerce.order;

import com.restaurant.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(String exception) {
        super(exception);
    }
}
