package com.web.offood.service;

import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.account.SignInResponse;
import com.web.offood.dto.email.OTPOfficeRequest;
import com.web.offood.dto.email.OTPRestaurantRequest;
import com.web.offood.dto.user.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    SignInResponse signIn(SignInRequest signInRequest);

    String signUp(RegisterRequest registerRequest);

    String verifyAccountRestaurant(OTPRestaurantRequest otpRequest);

    String verifyAccountOffice(OTPOfficeRequest otpRequest);
}
