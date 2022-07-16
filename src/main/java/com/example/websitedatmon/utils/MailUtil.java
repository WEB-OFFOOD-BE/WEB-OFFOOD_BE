package com.example.websitedatmon.utils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class MailUtil {


    public static void sendMail(JavaMailSenderImpl emailSender,String email,String title,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);
        emailSender.send(message);
    }

    public static void sendHtmlMail(JavaMailSenderImpl emailSender,String email,String title,String html)throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = html;

        message.setContent(htmlMsg, "text/html");

        helper.setTo(email);

        helper.setSubject(title);


        emailSender.send(message);

    }
}
