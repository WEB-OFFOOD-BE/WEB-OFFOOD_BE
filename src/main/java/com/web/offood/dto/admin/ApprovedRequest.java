package com.web.offood.dto.admin;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.Data;

@Data
public class ApprovedRequest {
    private Integer id;
    public void validate(){
        if (id == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }
}
