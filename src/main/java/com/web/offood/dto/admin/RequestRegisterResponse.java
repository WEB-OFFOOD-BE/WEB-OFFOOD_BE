package com.web.offood.dto.admin;

import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.RestaurantInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegisterResponse {
    private List<RestaurantInfo> restaurantInfos;
    private List<OfficeInfo> officeInfos;
}
