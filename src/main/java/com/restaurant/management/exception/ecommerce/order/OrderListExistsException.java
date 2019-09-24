package com.restaurant.management.exception.ecommerce.order;

import com.restaurant.management.exception.ExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderListExistsException extends ExistsException {
    public OrderListExistsException(String exception) {
        super(exception);
    }
}
