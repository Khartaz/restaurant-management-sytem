package com.restaurant.management.service;

import com.restaurant.management.domain.Mail;

public interface SimpleEmailService {

    void sendEmailVerification(final Mail mail, String token);

    void sendResetPasswordEmail(final Mail mail, String token);

}
