package com.web.offood.service.ServiceImpl;

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
            var account = accountRepository.findById(restaurantInfo.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
            restaurantInfo.setStatusId(RestaurantStatus.ACTIVE.getValue());
            restaurantRepository.save(restaurantInfo);
            //send mail
            EmailRequest emailRequest = EmailRequest.builder()
                    .subject("Cập nhật trạng thái!")
                    .content("Chúc mừng bạn đã được quản trị viên xác nhận tài khoản!")
                    .toTarget(account.getEmail())
                    .build();
            try {
                SendMailUtil.sendMail(emailRequest);
            } catch (EmailException e) {
                e.printStackTrace();
            }
            return "OK";
        }
        var office = officeRepository.findById(Id).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var account = accountRepository.findById(office.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        office.setStatusId(OfficeStatus.ACTIVE.getValue());
        officeRepository.save(office);
        //send mail
        EmailRequest emailRequest = EmailRequest.builder()
                .subject("Cập nhật trạng thái!")
                .content("Chúc mừng bạn đã được quản trị viên xác nhận tài khoản!")
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
    public String deleteRequest(Integer Id) {
        var restaurantInfo = restaurantRepository.findById(Id).orElse(null);
        if (restaurantInfo != null) {
            var account = accountRepository.findById(restaurantInfo.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
            //send mail
            EmailRequest emailRequest = EmailRequest.builder()
                    .subject("Cập nhật trạng thái!")
                    .content("Đăng ký của bạn đã bị huỷ bởi admin")
                    .toTarget(account.getEmail())
                    .build();
            try {
                SendMailUtil.sendMail(emailRequest);
            } catch (EmailException e) {
                e.printStackTrace();
            }
            restaurantRepository.delete(restaurantInfo);
            return "OK";
        }
        var office = officeRepository.findById(Id).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var account = accountRepository.findById(office.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        officeRepository.delete(office);
        //send mail
        EmailRequest emailRequest = EmailRequest.builder()
                .subject("Cập nhật trạng thái!")
                .content("Đăng ký của bạn đã bị huỷ bởi admin")
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
