package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.admin.RequestRegisterResponse;
import com.web.offood.dto.constant.status.OfficeStatus;
import com.web.offood.dto.constant.status.RestaurantStatus;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.RestaurantInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.AdminService;
import com.web.offood.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends BaseService implements AdminService {

    @Override
    public RequestRegisterResponse getAllRequestRegister() {

        List<RestaurantInfo> restaurantInfos = restaurantRepository.findAllByStatusId(RestaurantStatus.WAITING_CONFIRMATION.getValue());
        List<OfficeInfo> officeInfos = officeRepository.findAllByStatusId(RestaurantStatus.WAITING_CONFIRMATION.getValue());

        var resp = RequestRegisterResponse.builder()
                .restaurantInfos(restaurantInfos)
                .officeInfos(officeInfos)
                .build();

        return resp;
    }

    @Override
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

    @Override
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
