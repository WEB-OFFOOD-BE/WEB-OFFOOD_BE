package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.admin.RequestRegisterResponse;
import com.web.offood.dto.constant.OfficeStatus;
import com.web.offood.dto.constant.RestaurantStatus;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.RestaurantInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService {

    public RequestRegisterResponse getAllRequestRegister() {

        List<RestaurantInfo> restaurantInfos = restaurantRepository.findAllByStatusId(RestaurantStatus.WAITING_CONFIRMATION.getValue());
        List<OfficeInfo> officeInfos = officeRepository.findAllByStatusId(RestaurantStatus.WAITING_CONFIRMATION.getValue());

        var resp = RequestRegisterResponse.builder()
                .restaurantInfos(restaurantInfos)
                .officeInfos(officeInfos)
                .build();

        return resp;
    }

    public String approvedRequest(Integer Id) {
        var restaurantInfo = restaurantRepository.findById(Id).orElse(null);
        if (restaurantInfo != null) {
            restaurantInfo.setStatusId(RestaurantStatus.ACTIVE.getValue());
            restaurantRepository.save(restaurantInfo);
            return "OK";
        }
        var office = officeRepository.findById(Id).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        office.setStatusId(OfficeStatus.ACTIVE.getValue());
        officeRepository.save(office);
        return "OK";
    }

    public String deleteRequest(Integer Id) {
        var restaurantInfo = restaurantRepository.findById(Id).orElse(null);
        if (restaurantInfo != null) {
            restaurantRepository.delete(restaurantInfo);
            return "OK";
        }
        var office = officeRepository.findById(Id).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        officeRepository.delete(office);
        return "OK";
    }


}
