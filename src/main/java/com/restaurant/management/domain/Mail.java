package com.restaurant.management.domain;

public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String token;

    public Mail(String token) {

    }

    public Mail(String mailTo, String subject, String message) {
        this.mailTo = mailTo;
        this.subject = subject;
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

    public String getToken() {
        return token;
    }
}