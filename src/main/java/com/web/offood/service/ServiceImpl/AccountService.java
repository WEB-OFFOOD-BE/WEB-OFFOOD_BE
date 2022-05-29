package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.Mail;
import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.user.RegisterRequest;
import com.web.offood.entity.account.Account;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.security.JwtTokenProvider;
import com.web.offood.util.EmailUtil;
import com.web.offood.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    public String signIn(SignInRequest signInRequest) {
        signInRequest.validSignIn();
        if (accountRepository.findByUsername(signInRequest.getUsername()) == null) {
            throw new ApiException(ApiErrorCode.LOGIN_FAILED);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
            return jwtTokenProvider.createToken(signInRequest.getUsername(), accountRepository.findByUsername(signInRequest.getUsername()).getAccountRoles());
        } catch (AuthenticationException e) {
            throw new ApiException(ApiErrorCode.LOGIN_FAILED);
        }
    }


    public String signUp(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail().toLowerCase();
        String username = registerRequest.getUsername().toLowerCase();
        if (!TextUtils.isValidEmail(email)) {
            throw new ApiException(ApiErrorCode.INVALID_EMAIL);
        }
        if (registerRequest.getUsername() == null) {
            throw new ApiException(ApiErrorCode.INVALID_USERNAME);
        }
        ApiException.validPassword(registerRequest.getPassword());

        if (accountRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ApiException(ApiErrorCode.USERNAME_IN_USE);
        }

        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiException(ApiErrorCode.EMAIL_IN_USE);
        }
        char[] OTP = randomOTP(6);
        EmailUtil.composeEmail("Register OTP!", "OTP : " + OTP, email);

        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Account account = Account.builder()
                .username(username)
                .email(email)
                .password(registerRequest.getPassword())
                .accountRoles(registerRequest.getAccountRoles())
                .build();
        accountRepository.save(account);
        return "OK";
    }

    public String sendMail(RegisterRequest registerRequest) {
        char[] OTP = randomOTP(6);
        Mail mail = Mail.builder()
                .mailTo(registerRequest.getEmail())
                .mailFrom("offood.web@gmail.com")
                .mailContent("OTP : " + OTP)
                .build();
        sendEmail(mail);
//        EmailUtil.composeEmail("Register OTP!", "OTP : " + OTP, registerRequest.getEmail());
        return "OK";
    }
    @Autowired
    static JavaMailSender javaMailSender;

    public static void sendEmail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    static char[] randomOTP(int length) {

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String values = Capital_chars + numbers;
        Random random_method = new Random();

        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            password[i] =
                    values.charAt(random_method.nextInt(values.length()));
        }
        return password;
    }

}
