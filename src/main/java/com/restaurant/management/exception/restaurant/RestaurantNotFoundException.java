package com.restaurant.management.exception.restaurant;

import com.restaurant.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends NotFoundException {
    public RestaurantNotFoundException(String exception) {
        super(exception);
    }

}
