package com.web.offood.dto.restaurant;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class updateDiskRequest {
    private Integer id;
    private String name;
    private Double price;
    private String imageUrl;

    public void validate(){
        if (id == null || name == null || price == null || imageUrl == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }
}
