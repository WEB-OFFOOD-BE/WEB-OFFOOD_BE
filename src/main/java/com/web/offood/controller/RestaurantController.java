package com.web.offood.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.web.offood.dto.admin.RequestRegisterResponse;
import com.web.offood.dto.base.ActionBaseRequest;
import com.web.offood.dto.base.BaseResponse;
import com.web.offood.dto.base.ResponseBuilder;
import com.web.offood.dto.constant.action.ActionAccount;
import com.web.offood.dto.constant.action.ActionRestaurant;
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
@RequestMapping("/restaurant")
public class RestaurantController extends BaseService {

    @GetMapping("")
    public ResponseEntity<BaseResponse<RestaurantDetailResponse>> getRestaurantDetail(@RequestParam Integer id) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(restaurantService.getRestaurantDetail(id)).build());
    }

    @PostMapping("doAction")
    public ResponseEntity<BaseResponse<Object>> doActionRestaurant(
            @Valid @RequestBody ActionBaseRequest<Object> actionBaseRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(handleGenericAction(actionBaseRequest)).build());
    }

    /*
     * Generic actions handle for restaurant.
     * */
    private Object handleGenericAction(ActionBaseRequest<Object> request) {
        if (request.getAction() == null) throw new ApiException(ApiErrorCode.ACTION_INVALID);

        if (request.getAction().equals(ActionRestaurant.UPDATE_INFO.name())) {
            return restaurantService.updateRestaurant(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }

        if (request.getAction().equals(ActionRestaurant.CHANGE_AVATAR.name())) {
            return restaurantService.changeAvatar(UtilsApp.toTypeRef(request, new TypeReference<>() {
            }));
        }

        // TODO: Add new generic action logic below here!
        throw new ApiException(ApiErrorCode.ACTION_INVALID);
    }
}
