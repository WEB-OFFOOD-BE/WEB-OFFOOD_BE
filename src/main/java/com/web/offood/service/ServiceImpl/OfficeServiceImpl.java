package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.constant.status.OfficeStatus;
import com.web.offood.dto.office.OfficeDetailResponse;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.BaseService;
import com.web.offood.service.OfficeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl extends BaseService implements OfficeService {
    public List<OfficeInfo> getAllOfficeActivated() {
        return officeRepository.findAllByStatusId(OfficeStatus.ACTIVE.getValue());
    }

    public List<OfficeInfo> getAllOfficeUnverified() {
        return officeRepository.findAllByStatusId(OfficeStatus.ACTIVE_BUT_UNVERIFIED.getValue());
    }

    public List<OfficeInfo> getAllOfficeLock() {
        return officeRepository.findAllByStatusId(OfficeStatus.LOCK.getValue());
    }

    public List<OfficeInfo> getAllOfficeWaitingConfirm() {
        return officeRepository.findAllByStatusId(OfficeStatus.ACTIVE_BUT_UNVERIFIED.getValue());
    }

    public OfficeDetailResponse getDetailOffice(Integer officeId) {

        var office = officeRepository.findById(officeId).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var account = accountRepository.findById(office.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var staffs = staffRepository.findAllByOfficeId(office.getId());

        OfficeDetailResponse response = OfficeDetailResponse.builder()
                .staff(staffs)
                .officeInfo(office)
                .email(account.getEmail())
                .build();

        return response;
    }
}
