package com.web.offood.dto.email;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPOfficeRequest {
    private String otp;
    private String email;
    private String phoneNumber;
    private String description;
    private String code;
    private String address;
    private String taxCode;

    public void validate() {
        if (otp == null || email == null || phoneNumber == null || code == null || address == null || taxCode == null
        )
            throw new ApiException(ApiErrorCode.INPUT_INVALID);

    }
}
