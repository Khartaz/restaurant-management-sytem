package com.restaurant.management.service;

import com.restaurant.management.security.SecurityConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildVerificationEmail(String message, String token) {

        String verificationURL = SecurityConstans.BASE_URL + "/auth/email-verification?token=" + token;

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", verificationURL);
        context.setVariable("button", "Verify Email");
        context.setVariable("show_button", true);
        return templateEngine.process("mail/emailVerification", context);
    }
}
