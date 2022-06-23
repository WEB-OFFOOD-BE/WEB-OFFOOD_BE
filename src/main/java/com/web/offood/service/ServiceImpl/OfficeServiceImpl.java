package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.office.AddStaffRequest;
import com.web.offood.dto.office.OfficeDetailResponse;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.Staff;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.BaseService;
import com.web.offood.service.OfficeService;
import com.web.offood.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl extends BaseService implements OfficeService {

    @Override
    public List<OfficeInfo> getAllOfficeActivated() {
        return null;
    }

    @Override
    public List<OfficeInfo> getAllOfficeUnverified() {
        return null;
    }

    @Override
    public List<OfficeInfo> getAllOfficeLock() {
        return null;
    }

    @Override
    public List<OfficeInfo> getAllOfficeWaitingConfirm() {
        return null;
    }

    @Override
    public OfficeDetailResponse getDetailOffice(Integer officeId) {
        if (officeId == null) {
            throw new ApiException(ApiErrorCode.INPUT_INVALID);
        }
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

    @Override
    public String addStaffOrUpdate(AddStaffRequest request) {
        request.validate();
        var office = officeRepository.findById(request.getOfficeId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var staff = staffRepository.findById(request.getStaffId()).orElse(null);
        if (staff == null){
            staff = Staff.builder()
                    .officeId(office.getId())
                    .createDate(TimeUtils.convertToTimestamp())
                    .name(request.getStaffName())
                    .isActive(true)
                    .todayChoose(false)
                    .build();
        }
        else {
            staff.setName(request.getStaffName());
        }
        staffRepository.save(staff);
        return "OK";
    }

    @Override
    public String deleteStaff(AddStaffRequest request) {
        request.validate();
        var staff = staffRepository.findById(request.getStaffId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        staff.setIsActive(false);
        staffRepository.save(staff);
        return "OK";
    }

}
