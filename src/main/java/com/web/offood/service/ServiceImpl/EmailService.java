//package com.web.offood.service.ServiceImpl;
//
//import com.web.offood.dto.email.EmailRequest;
//import com.web.offood.util.SendMailUtil;
//import org.apache.commons.mail.EmailException;
//import org.springframework.stereotype.Service;
//
//
//import java.util.Random;
//
//@Service
//public class EmailService extends BaseService {
//
//    public String sendOTP(String emailTarget) throws EmailException {
//        EmailRequest emailRequest = new EmailRequest();
//        //random OTP
//        String OTP = String.valueOf(randomOTP(6));
//        emailRequest.setToTarget(emailTarget);
//        emailRequest.setSubject("Your OTP!");
//        emailRequest.setContent("OTP: " + OTP);
//        SendMailUtil.sendMail(emailRequest);
//        return "OK";
//    }
//
//    static char[] randomOTP(int length) {
//
//        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String numbers = "0123456789";
//        String values = Capital_chars + numbers;
//        Random random_method = new Random();
//
//        char[] password = new char[length];
//
//        for (int i = 0; i < length; i++) {
//            password[i] =
//                    values.charAt(random_method.nextInt(values.length()));
//        }
//        return password;
//    }
//}
