package com.web.offood.exception;

public enum ApiErrorCode {
    UNAUTHORIZED(-1, "Unauthorized"),
    LOGIN_FAILED(-2, "Username or password is incorrect."),
    ACTION_INVALID(-3, "Action invalid"),
    INPUT_INVALID(-4, "Params is not empty!"),
    BALANCE_NOT_ENOUGH(-5, "Your balance is not enough"),
    USERNAME_NOT_EXIST(-6, "Username not exist."),
    INCORRECT_HEADER_REQUEST(-7, "Incorrect header request"),
    USER_NOT_EXIST(-8, "User not exist."),
    SIGNATURE_INCORRECT(-9, "signature incorrect"),
    TIME_OVERDUE_VERIFY(-10, "Time overdue for verification"),
    NONCE_NOT_FOUND(-11, "Nonce not found!"),
    OBJECT_NOT_FOUND(-12, "Object is not found"),
    TOKEN_EXPIRED(-13, "Token expired."),
    USER_NOT_ACTIVE(-14, "User not active"),
    USER_EXIST(-15, "User exist."),
    LIMIT_EMAIL_QUANTITY(-16, "The number of request has been exceeded!"),
    EMAIL_NOT_EMPTY(-17, "Email not empty!"),
    OTP_NOT_EMPTY(-18, "Otp not empty!"),
    EMAIL_OTP_EXPIRED(-19, "Email OTP is expired!"),
    ACCOUNT_NOT_VERIFY(-20, "Unverified account"),
    ADDRESS_NOT_FOUND(-21, "Address is not found"),
    EMAIL_OTP_INCORRECT(-22, "Email OTP is incorrect!"),
    PASSWORD_FORMAT_INCORRECT(-23, "Password contain only a-z, A-Z, 0-9, 8 to 30 chars!"),
    USER_VERIFIED(-24, "!"),
    USERNAME_NOT_EMPTY(-25, "Username not empty"),
    PASSWORD_NOT_EMPTY(-26, "Password not empty"),
    INVALID_EMAIL(-27, "Email invalid"),
    INVALID_USERNAME(-28, "Username invalid"),
    USERNAME_IN_USE(-29, "Username already in use."),
    EMAIL_IN_USE(-30, "Email already in use."),
    EMAIL_REWARD_SUBJECT_INVALID(-31, "Subject is invalid!"),
    EMAIL_REWARD_CONTENT_INVALID(-32, "Content is invalid!"),


    ;

    private final Integer errorCode;
    private final String message;

    ApiErrorCode(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
