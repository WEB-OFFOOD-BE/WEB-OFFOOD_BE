package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.constant.OfficeStatus;
import com.web.offood.dto.constant.RestaurantStatus;
import com.web.offood.dto.email.EmailRequest;
import com.web.offood.dto.email.OTPRequest;
import com.web.offood.dto.user.RegisterRequest;
import com.web.offood.entity.account.Account;
import com.web.offood.entity.account.AccountRoles;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.RestaurantInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.redis.RedisKey;
import com.web.offood.security.JwtTokenProvider;
import com.web.offood.util.SendMailUtil;
import com.web.offood.util.TextUtils;
import com.web.offood.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.EmailException;
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
        registerRequest.validate();
        String email = registerRequest.getEmail().toLowerCase();
        String username = registerRequest.getUsername().toLowerCase();
        if (!TextUtils.isValidEmail(email)) {
            throw new ApiException(ApiErrorCode.INVALID_EMAIL);
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

        if (registerRequest.getAccountRoles().get(0).toString().equals("ROLE_RESTAURANT")) {
            RestaurantInfo restaurantInfo = RestaurantInfo.builder()
                    .name(registerRequest.getName())
                    .accountId(account.getId())
                    .statusId(RestaurantStatus.ACTIVE_BUT_UNVERIFIED.getValue())
                    .avatarUrl("")
                    .createDate(TimeUtils.convertToTimestamp())
                    .businessCode("0")
                    .description("Mô tả")
                    .ratePoint(0.0)
                    .phoneNumber("0")
                    .build();
            restaurantRepository.save(restaurantInfo);
        }
        if (registerRequest.getAccountRoles().get(0) == AccountRoles.ROLE_OFFICE) {
            OfficeInfo officeInfo = OfficeInfo.builder()
                    .name(registerRequest.getName())
                    .accountId(account.getId())
                    .statusId(OfficeStatus.ACTIVE_BUT_UNVERIFIED.getValue())
                    .build();
            officeRepository.save(officeInfo);
        }
//        if (registerRequest.getAccountRoles().get(0) == AccountRoles.ROLE_ADMIN) {
//            OfficeInfo officeInfo =  OfficeInfo.builder()
//                    .name(registerRequest.getName())
//                    .accountId(account.getId())
//                    .statusId(OfficeStatus.ACTIVE_BUT_UNVERIFIED.getValue())
//                    .build();
//            officeRepository.save(officeInfo);
//        }

        //gủi OTP
        try {
            EmailRequest emailRequest = new EmailRequest();
            var otp = String.valueOf(randomOTP(6));
            emailRequest.setToTarget(account.getEmail());
            emailRequest.setSubject("Mã OTP của bạn!");
            emailRequest.setContent("OTP: " + otp);
            SendMailUtil.sendMail(emailRequest);
            redisComponent.set(RedisKey.OTP.name(), account.getEmail(), otp);
        } catch (EmailException e) {
            throw new ApiException(ApiErrorCode.ERROR_SEND_MAIL);
        }

        return "OK";
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

    public String verifyAccount(OTPRequest otpRequest) {
        otpRequest.validate();
        var account = accountRepository.findByEmail(otpRequest.getEmail());

        if (account == null) {
            throw new ApiException(ApiErrorCode.EMAIL_NOT_EMPTY);
        }

        var restaurant =
                restaurantRepository.findByAccountIdAndStatusId(account.getId(), RestaurantStatus.ACTIVE_BUT_UNVERIFIED.getValue());
        if (restaurant == null) {
            var office =
                    officeRepository.findByAccountIdAndStatusId(account.getId(), OfficeStatus.ACTIVE_BUT_UNVERIFIED.getValue());
            if (office == null){
                throw new ApiException(ApiErrorCode.ACCOUNT_NOT_EXIST);
            }
        }

        String otp = redisComponent.get(RedisKey.OTP.name(), otpRequest.getEmail());
        if (!otpRequest.getOtp().equals(otp)) {
            throw new ApiException(ApiErrorCode.OTP_NOT_EMPTY);
        }
        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setToTarget(otpRequest.getEmail());
            emailRequest.setSubject("Tạo tài khoản thành công!");
            emailRequest.setContent("Chúc mừng bạn đã tạo tài khoản thành công");
            SendMailUtil.sendMail(emailRequest);
        } catch (EmailException e) {
            throw new ApiException(ApiErrorCode.ACTION_INVALID);
        }

        return "OK";
    }
}
