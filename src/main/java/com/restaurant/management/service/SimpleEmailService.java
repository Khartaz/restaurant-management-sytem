package com.restaurant.management.service;

import com.restaurant.management.domain.Mail;

public interface SimpleEmailService {

    void sendEmailVerification( Mail mail, String token);

    void sendResetPasswordEmail( Mail mail, String token);

}
