//package com.web.offood.service.ServiceImpl;
//
//import com.web.offood.dto.Mail;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//@Service
//public class SendMailService {
//    JavaMailSender javaMailSender;
//
//    public void sendEmail(Mail mail) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setSubject(mail.getMailSubject());
//            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
//            mimeMessageHelper.setTo(mail.getMailTo());
//            mimeMessageHelper.setText(mail.getMailContent());
//            javaMailSender.send(mimeMessageHelper.getMimeMessage());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//}
