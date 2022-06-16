package com.web.offood.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.web.offood.dto.base.ActionBaseRequest;
import com.web.offood.dto.base.BaseResponse;
import com.web.offood.dto.base.ResponseBuilder;
import com.web.offood.dto.constant.action.ActionOffice;
import com.web.offood.dto.restaurant.RestaurantDetailResponse;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.BaseService;
import com.web.offood.util.UtilsApp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/office")
public class OfficeController extends BaseService {

    @GetMapping("getDetailOffice")
    public ResponseEntity<BaseResponse<RestaurantDetailResponse>> getDetailOffice(@RequestParam Integer id) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(officeService.getDetailOffice(id)).build());
    }

    @PostMapping("doAction")
    public ResponseEntity<BaseResponse<Object>> doActionAdmin(
            @Valid @RequestBody ActionBaseRequest<Object> actionBaseRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(handleGenericAction(actionBaseRequest)).build());
    }

    /*
     * Generic actions handle for office.
     * */
    private Object handleGenericAction(ActionBaseRequest<Object> request) {
        if (request.getAction() == null) throw new ApiException(ApiErrorCode.ACTION_INVALID);

        if (request.getAction().equals(ActionOffice.ADD_OR_UPDATE_STAFF.name())) {
            return officeService.addStaffOrUpdate(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }

        if (request.getAction().equals(ActionOffice.DELETE_STAFF.name())) {
            return officeService.deleteStaff(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }

        // TODO: Add new generic action logic below here!
        throw new ApiException(ApiErrorCode.ACTION_INVALID);
    }
}
