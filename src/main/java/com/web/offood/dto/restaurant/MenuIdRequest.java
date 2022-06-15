package com.web.offood.dto.restaurant;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.Data;

@Data
public class MenuIdRequest {
    private Integer menuId;
    public void validate(){
        if (menuId == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }
}
