package com.web.offood.dto.restaurant;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.Data;

@Data
public class AddDiskOnMenuRequest {
    private Integer menuId;
    private Integer diskId;

    public void validate() {
        if (menuId == null || diskId == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }
}
