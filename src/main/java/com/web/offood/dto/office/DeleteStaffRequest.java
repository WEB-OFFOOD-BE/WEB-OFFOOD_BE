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
public class DeleteStaffRequest {
    private Integer staffId;
    public void validate(){
        if (staffId == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }
}
