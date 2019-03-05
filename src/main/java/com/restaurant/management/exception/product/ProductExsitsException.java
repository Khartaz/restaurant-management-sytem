package com.restaurant.management.exception.product;

import com.restaurant.management.exception.ExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductExsitsException extends ExistsException {
    public ProductExsitsException(String exception) {
        super(exception);
    }
}
