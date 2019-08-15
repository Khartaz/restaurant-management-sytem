package com.restaurant.management.utils;

import com.restaurant.management.exception.ExceptionMessage;
import com.restaurant.management.exception.ValidationException;

import java.util.regex.Pattern;

public class Validation {

    public static boolean validatePhoneNumberFormat(String phone) {
        Pattern pattern = Pattern.compile("^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$");
        boolean result = pattern.matcher(phone).matches();

        if (!result) {
            throw new ValidationException(ExceptionMessage.INVALID_PHONE_NUMBER.getMessage());
        }
        return true;
    }
}
