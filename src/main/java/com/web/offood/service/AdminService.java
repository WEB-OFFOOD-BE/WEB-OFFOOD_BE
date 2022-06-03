package com.web.offood.service;

import com.web.offood.dto.admin.RequestRegisterResponse;

public interface AdminService {
    RequestRegisterResponse getAllRequestRegister();

    String approvedRequest(Integer Id);

    String deleteRequest(Integer Id);
}
