package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.admin.ApprovedRequest;
import com.web.offood.dto.admin.RequestRegisterResponse;
import com.web.offood.dto.constant.status.OfficeStatus;
import com.web.offood.dto.constant.status.RestaurantStatus;
import com.web.offood.dto.email.EmailRequest;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.RestaurantInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.AdminService;
import com.web.offood.service.BaseService;
import com.web.offood.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends BaseService implements AdminService {

    @Override
    public RequestRegisterResponse getAllRequestRegister() {

        List<RestaurantInfo> restaurantInfos = restaurantRepository.findAllByStatusIdAndIsActive(RestaurantStatus.WAITING_CONFIRMATION.getValue(), true);
        List<OfficeInfo> officeInfos = officeRepository.findAllByStatusIdAndIsActive(RestaurantStatus.WAITING_CONFIRMATION.getValue(), true);

        var resp = RequestRegisterResponse.builder()
                .restaurantInfos(restaurantInfos)
                .officeInfos(officeInfos)
                .build();

        return resp;
    }

    @Override
    public RequestRegisterResponse getAllRequest() {

        List<RestaurantInfo> restaurantInfos = restaurantRepository.findAllByIsActive(true);
        List<OfficeInfo> officeInfos = officeRepository.findAllByIsActive(true);

        var resp = RequestRegisterResponse.builder()
                .restaurantInfos(restaurantInfos)
                .officeInfos(officeInfos)
                .build();

        return resp;
    }

    @Override
    public String approvedRequest(ApprovedRequest request) {
        request.validate();
        var restaurantInfo = restaurantRepository.findByIdAndIsActive(request.getId(), true);
        if (restaurantInfo != null) {
            var account = accountRepository.findById(restaurantInfo.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
            restaurantInfo.setStatusId(RestaurantStatus.ACTIVE.getValue());
            restaurantRepository.save(restaurantInfo);
            //send mail
            EmailRequest emailRequest = EmailRequest.builder()
                    .subject("C???p nh???t tr???ng th??i!")
                    .content("Ch??c m???ng b???n ???? ???????c qu???n tr??? vi??n x??c nh???n t??i kho???n!")
                    .toTarget(account.getEmail())
                    .build();
            try {
                SendMailUtil.sendMail(emailRequest);
            } catch (EmailException e) {
                e.printStackTrace();
            }
            return "OK";
        }
        var office = officeRepository.findByIdAndIsActive(request.getId(), true);
        if (office == null){
            throw new ApiException(ApiErrorCode.OBJECT_NOT_FOUND);
        }
        var account = accountRepository.findById(office.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        office.setStatusId(OfficeStatus.ACTIVE.getValue());
        officeRepository.save(office);
        //send mail
        EmailRequest emailRequest = EmailRequest.builder()
                .subject("C???p nh???t tr???ng th??i!")
                .content("Ch??c m???ng b???n ???? ???????c qu???n tr??? vi??n x??c nh???n t??i kho???n!")
                .toTarget(account.getEmail())
                .build();
        try {
            SendMailUtil.sendMail(emailRequest);
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @Override
    public String deleteRequest(ApprovedRequest request) {
        request.validate();
        var restaurantInfo = restaurantRepository.findByIdAndIsActive(request.getId(), true);
        if (restaurantInfo != null) {
            var account = accountRepository.findById(restaurantInfo.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
            restaurantInfo.setIsActive(false);
            restaurantRepository.save(restaurantInfo);
            //send mail
            EmailRequest emailRequest = EmailRequest.builder()
                    .subject("C???p nh???t tr???ng th??i!")
                    .content("????ng k?? c???a b???n ???? b??? hu??? b???i admin")
                    .toTarget(account.getEmail())
                    .build();
            try {
                SendMailUtil.sendMail(emailRequest);
            } catch (EmailException e) {
                e.printStackTrace();
            }

            return "OK";
        }
        var office = officeRepository.findByIdAndIsActive(request.getId(), true);
        if (office == null){
            throw new ApiException(ApiErrorCode.OBJECT_NOT_FOUND);
        }
        var account = accountRepository.findById(office.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        office.setIsActive(false);
        officeRepository.save(office);
        //send mail
        EmailRequest emailRequest = EmailRequest.builder()
                .subject("C???p nh???t tr???ng th??i!")
                .content("????ng k?? c???a b???n ???? b??? hu??? b???i admin")
                .toTarget(account.getEmail())
                .build();
        try {
            SendMailUtil.sendMail(emailRequest);
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return "OK";
    }

}
