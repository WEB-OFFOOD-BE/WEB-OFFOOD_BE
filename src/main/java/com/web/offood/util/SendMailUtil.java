package com.web.offood.util;

import com.web.offood.config.MailConfig;
import com.web.offood.dto.email.EmailRequest;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SendMailUtil {
    public static String sendMail(EmailRequest emailRequest) throws EmailException {

        Email email = new SimpleEmail();

        // config server
        email.setHostName(MailConfig.HOST_NAME);
        email.setSmtpPort(MailConfig.SSL_PORT);
        email.setAuthenticator(new DefaultAuthenticator(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD));
        email.setSSLOnConnect(true);

        email.setFrom(MailConfig.APP_EMAIL);
        email.addTo(emailRequest.getToTarget());
        email.setSubject(emailRequest.getSubject());
        email.setMsg(emailRequest.getContent());
        email.send();
        return "OK";
    }
}
