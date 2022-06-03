package com.web.offood.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.base.ActionBaseRequest;
import com.web.offood.dto.base.BaseResponse;
import com.web.offood.dto.base.ResponseBuilder;
import com.web.offood.dto.constant.action.ActionAccount;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.BaseService;
import com.web.offood.util.UtilsApp;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/account")
@Api(tags = "account")
@RequiredArgsConstructor
public class AccountController extends BaseService {

    @PostMapping("doAction")
    public ResponseEntity<BaseResponse<Object>> doActionAccount(
            @Valid @RequestBody ActionBaseRequest<Object> actionBaseRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(handleGenericAction(actionBaseRequest)).build());
    }

    /*
     * Generic actions handle for account.
     * */
    private Object handleGenericAction(ActionBaseRequest<Object> request) {
        if (request.getAction() == null) throw new ApiException(ApiErrorCode.ACTION_INVALID);

        if (request.getAction().equals(ActionAccount.LOGIN.name())) {
            return accountService.signIn(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }
        if (request.getAction().equals(ActionAccount.SIGNUP.name())) {
            return accountService.signUp(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }
        if (request.getAction().equals(ActionAccount.VERIFY_RESTAURANT.name())) {
            return accountService.verifyAccountRestaurant(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }if (request.getAction().equals(ActionAccount.VERIFY_OFFICE.name())) {
            return accountService.verifyAccountOffice(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }


        // TODO: Add new generic action logic below here!
        throw new ApiException(ApiErrorCode.ACTION_INVALID);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(
            @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(accountService.signIn(signInRequest)).build());
    }


}
