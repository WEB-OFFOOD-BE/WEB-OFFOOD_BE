package com.web.offood.dto.email;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.Getter;

@Getter
public class OTPRequest {
    private String otp;
    private String email;
    public void validate(){
        if(otp ==null) throw new ApiException(ApiErrorCode.INPUT_INVALID);

    }
}
