package com.web.offood.dto.office;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddStaffRequest {
    private Integer officeId;
    private Integer staffId;
    private String staffName;
    public void validate(){
        if (officeId == null || staffId == null || staffName == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }
}
