package com.web.offood.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    public static Environment env;

    public static SimpleMailMessage composeEmail(final String subject, final String messageText, final String toTarget) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(messageText);
        message.setTo(toTarget);
        message.setFrom("offood.web@gmail.com");
        return message;
    }


}
