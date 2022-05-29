package com.web.offood.exception;


import com.web.offood.util.TextUtils;

/**
 * @author quanglb Create at 5/26/22
 */

public class ApiException extends RuntimeException{
    private String objectName;
    private Object objectValue;
    private ApiErrorCode apiErrorCode;
    private Integer previous;
    private Object data;
    private Boolean isFormat = false;

    public ApiException(String objectName, Object objectValue, ApiErrorCode apiErrorCode) {
        this.objectName = objectName;
        this.objectValue = objectValue;
        this.apiErrorCode = apiErrorCode;
    }

    public ApiException(Object objectValue, ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
        this.objectValue = objectValue;
    }

    public ApiException(String objectName, ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
        this.objectName = objectName;
    }

    public ApiException(ApiErrorCode apiErrorCode) {
        super();
        this.apiErrorCode = apiErrorCode;
    }

    public ApiException(ApiErrorCode apiErrorCode, int isPrevious, Object objectValue) {
        this.apiErrorCode = apiErrorCode;
        this.objectValue = objectValue;
        this.previous = isPrevious;
    }

    public ApiException(ApiErrorCode apiErrorCode, Object data) {
        this.apiErrorCode = apiErrorCode;
        this.data = data;
    }

    public ApiException(ApiErrorCode apiErrorCode, Boolean isFormat, String objectName) {
        this.apiErrorCode = apiErrorCode;
        this.isFormat = isFormat;
        this.objectName = objectName;
    }

    public static void validPassword(String password) {
        if (!TextUtils.checkPassword(password)) {
            throw new ApiException(ApiErrorCode.PASSWORD_FORMAT_INCORRECT);
        }
    }

    public ApiErrorCode getApiErrorCode() {
        return apiErrorCode;
    }

    public void setApiErrorCode(ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public String getObjectName() {
        return objectName;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    public Integer getIsPrevious() {
        return previous;
    }

    public Object getData() {
        return data;
    }

    public Boolean getIsFormat() {
        return isFormat;
    }
}