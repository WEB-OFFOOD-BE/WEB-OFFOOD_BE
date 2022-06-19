package com.web.offood.service;

import com.web.offood.dto.admin.ApprovedRequest;
import com.web.offood.dto.admin.RequestRegisterResponse;

public interface AdminService {
    RequestRegisterResponse getAllRequestRegister();

    RequestRegisterResponse getAllRequest();

    String approvedRequest(ApprovedRequest request);

    String deleteRequest(ApprovedRequest request);
}
