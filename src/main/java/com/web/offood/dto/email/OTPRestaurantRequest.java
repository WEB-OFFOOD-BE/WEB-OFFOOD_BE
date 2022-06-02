package com.web.offood.dto.email;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPRestaurantRequest {
    private String otp;
    private String email;
    private String phoneNumber;
    private String description;
    private String code;
    private String address;

    private String imgURL_1;
    private String imgURL_2;
    private String imgURL_3;
    private String imgURL_4;
    private String imgURL_5;

    private String nameDisk_1;
    private String nameDisk_2;
    private String nameDisk_3;
    private String nameDisk_4;
    private String nameDisk_5;

    private Float priceDisk_1;
    private Float priceDisk_2;
    private Float priceDisk_3;
    private Float priceDisk_4;
    private Float priceDisk_5;

    public void validate() {
        if (otp == null || email == null || phoneNumber == null || code == null || address == null
                || nameDisk_1 == null || nameDisk_2 == null || nameDisk_3 == null || nameDisk_4 == null || nameDisk_5 == null
                || imgURL_1 == null || imgURL_2 == null || imgURL_3 == null || imgURL_4 == null || imgURL_5 == null
                || priceDisk_1 == null || priceDisk_2 == null || priceDisk_3 == null || priceDisk_4 == null || priceDisk_5 == null
        )
            throw new ApiException(ApiErrorCode.INPUT_INVALID);

    }
}
