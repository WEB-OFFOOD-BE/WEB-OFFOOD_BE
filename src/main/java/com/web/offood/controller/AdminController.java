package com.web.offood.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.web.offood.dto.admin.RequestRegisterResponse;
import com.web.offood.dto.base.ActionBaseRequest;
import com.web.offood.dto.base.BaseResponse;
import com.web.offood.dto.base.ResponseBuilder;
import com.web.offood.dto.constant.ActionAdmin;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.ServiceImpl.AdminService;
import com.web.offood.util.UtilsApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<RequestRegisterResponse>> getAllRequestRegister() {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(adminService.getAllRequestRegister()).build());
    }

    @PostMapping("doAction")
    public ResponseEntity<BaseResponse<Object>> doActionCharacter(
            @Valid @RequestBody ActionBaseRequest<Object> actionBaseRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(handleGenericActionCharacter(actionBaseRequest)).build());
    }

    /*
     * Generic actions handle for admin.
     * */
    private Object handleGenericActionCharacter(ActionBaseRequest<Object> request) {
        if (request.getAction() == null) throw new ApiException(ApiErrorCode.ACTION_INVALID);

        if (request.getAction().equals(ActionAdmin.APPROVAL_REQUEST.name())) {
            return adminService.approvedRequest(
                    UtilsApp.toTypeRef(request, new TypeReference<>() {
                    }));
        }

        if (request.getAction().equals(ActionAdmin.DELETE_REQUEST.name())) {
            return adminService.deleteRequest(
                    UtilsApp.toTypeRef(request, new TypeReference<>() {
                    }));
        }

        // TODO: Add new generic action logic below here!
        throw new ApiException(ApiErrorCode.ACTION_INVALID);
    }
}
