package com.web.offood.service;

import com.web.offood.dto.office.AddStaffRequest;
import com.web.offood.dto.office.OfficeDetailResponse;
import com.web.offood.entity.office.OfficeInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfficeService {
    List<OfficeInfo> getAllOfficeActivated();

    List<OfficeInfo> getAllOfficeUnverified();

    List<OfficeInfo> getAllOfficeLock();

    List<OfficeInfo> getAllOfficeWaitingConfirm();

    OfficeDetailResponse getDetailOffice(Integer officeId);

    String addStaffOrUpdate(AddStaffRequest request);

    String deleteStaff(AddStaffRequest request);
}
