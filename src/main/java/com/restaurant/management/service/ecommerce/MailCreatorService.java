package com.restaurant.management.service.ecommerce;

public interface MailCreatorService {

    String buildVerificationEmail(String message, String token);

    String buildPasswordResetEmail(String message, String token);
}
