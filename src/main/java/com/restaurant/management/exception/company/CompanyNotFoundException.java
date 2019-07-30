package com.restaurant.management.exception.company;

import com.restaurant.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(String exception) {
        super(exception);
    }

}
