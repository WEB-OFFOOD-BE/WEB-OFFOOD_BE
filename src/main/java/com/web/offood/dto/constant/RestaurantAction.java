package com.web.offood.dto.constant;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;

import java.util.Objects;

public enum RestaurantAction {
    CREATE_MENU,
    ADD_DISK;

    public static RestaurantAction getAction(String action) {
        RestaurantAction response = null;
        for (RestaurantAction item : RestaurantAction.values()) {
            if (Objects.equals(item.name(), action)) {
                response = item;
                break;
            }
        }
        if (response == null) throw new ApiException(ApiErrorCode.OBJECT_NOT_FOUND);
        return response;
    }
}
