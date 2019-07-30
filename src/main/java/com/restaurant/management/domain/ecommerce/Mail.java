package com.restaurant.management.domain.ecommerce;

public class Mail {
    private String mailTo;
    private String subject;
    private String message;


    public Mail(String mailTo, String message) {
        this.mailTo = mailTo;
        this.message = message;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

}