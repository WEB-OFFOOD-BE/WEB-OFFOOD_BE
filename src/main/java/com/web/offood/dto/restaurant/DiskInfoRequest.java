package com.web.offood.dto.restaurant;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiskInfoRequest {
    private Integer menuId;
    private Integer id;
    private String foodName;
    private String imageUrl;
    private Float price;
    private Integer typeId;

    private String description;

    public void validate(){
        if (menuId == null || foodName == null || price == null) throw new ApiException(ApiErrorCode.INPUT_INVALID);
    }

}
