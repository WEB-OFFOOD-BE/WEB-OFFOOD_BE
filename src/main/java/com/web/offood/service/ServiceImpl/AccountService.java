package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.email.EmailRequest;
import com.web.offood.dto.user.RegisterRequest;
import com.web.offood.entity.account.Account;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.security.JwtTokenProvider;
import com.web.offood.util.SendMailUtil;
import com.web.offood.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public static final String HOST_NAME = "smtp.gmail.com";

    public static final int SSL_PORT = 465; // Port for SSL

    public static final int TSL_PORT = 587; // Port for TLS/STARTTLS

    public static final String APP_EMAIL = "quangxa14@gmail.com"; // your email

    public static final String APP_PASSWORD = "xufqvnpqsgovprbx"; // your password

    public static final String RECEIVE_EMAIL = "quangxa14@gmail.com";


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

}
