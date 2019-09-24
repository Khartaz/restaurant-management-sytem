package com.restaurant.management.service.ecommerce;

import com.restaurant.management.domain.ecommerce.Mail;

public interface SimpleEmailService {

    void sendEmailVerification( Mail mail, String token);

    void sendResetPasswordEmail( Mail mail, String token);

}
