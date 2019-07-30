package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.Mail;
import com.restaurant.management.service.MailCreatorService;
import com.restaurant.management.service.SimpleEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailServiceImpl implements SimpleEmailService {

    private MailCreatorService mailCreatorService;
    private JavaMailSender javaMailSender;

    @Autowired
    public SimpleEmailServiceImpl(MailCreatorService mailCreatorService) {
        this.mailCreatorService = mailCreatorService;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);
    private static final String SUBJECT_VERIFY_EMAIL = "Email verification Restaurant Admin System";
    private static final String SUBJECT_RESET_PASSWORD = "Email password reset Restaurant Admin System";


    public void sendEmailVerification(final Mail mail, String token) {

        LOGGER.info("Starting email preparation...");
        try {

            javaMailSender.send(createVerificationEmail(mail, token));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {

            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createVerificationEmail(final Mail mail, String token) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(SUBJECT_VERIFY_EMAIL);
            messageHelper.setText(mailCreatorService.buildVerificationEmail(mail.getMessage(), token),true);
        };
    }

    public void sendResetPasswordEmail(final Mail mail, String token) {

        LOGGER.info("Starting email preparation...");
        try {

            javaMailSender.send(createResetPasswordEmail(mail, token));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {

            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createResetPasswordEmail(final Mail mail, String token) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(SUBJECT_RESET_PASSWORD);
            messageHelper.setText(mailCreatorService.buildPasswordResetEmail(mail.getMessage(), token),true);
        };
    }
}
