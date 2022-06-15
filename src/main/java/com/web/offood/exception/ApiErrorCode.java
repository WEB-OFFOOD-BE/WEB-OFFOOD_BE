package com.web.offood.exception;

public enum ApiErrorCode {
    UNAUTHORIZED(-1, "không được phép"),
    LOGIN_FAILED(-2, "Tên đăng nhập hoặc tài khoản của bạn không chính xác"),
    ACTION_INVALID(-3, "Hành động không hợp lệ"),
    INPUT_INVALID(-4, "Tham số không trống"),
    BALANCE_NOT_ENOUGH(-5, "Số dư của bạn không đủ"),
    USERNAME_NOT_EXIST(-6, "Tên người dùng không tồn tại"),
    INCORRECT_HEADER_REQUEST(-7, "Yêu cầu tiêu đề không chính xác"),
    USER_NOT_EXIST(-8, "tài khoản không tồn tại"),
    SIGNATURE_INCORRECT(-9, "chữ ký không chính xác"),
    TIME_OVERDUE_VERIFY(-10, "Thời gian quá hạn để xác minh"),
    NONCE_NOT_FOUND(-11, "Nonce không tìm thấy"),
    OBJECT_NOT_FOUND(-12, "Đối tượng không được tìm thấy"),
    TOKEN_EXPIRED(-13, "Mã thông báo hết hạn"),
    USER_NOT_ACTIVE(-14, "Người dùng không hoạt động"),
    USER_EXIST(-15, "người dùng tồn tại"),
    LIMIT_EMAIL_QUANTITY(-16, "số lượng yêu cầu đã được vượt quá"),
    EMAIL_NOT_EMPTY(-17, "Email không trống"),
    OTP_NOT_EMPTY(-18, "Otp không trống"),
    EMAIL_OTP_EXPIRED(-19, "Email OTP đã hết hạn"),
    ACCOUNT_NOT_VERIFY(-20, "Tài khoản chưa được xác minh"),
    ADDRESS_NOT_FOUND(-21, "Địa chỉ không được tìm thấy"),
    EMAIL_OTP_INCORRECT(-22, "Email OTP không chính xác"),
    PASSWORD_FORMAT_INCORRECT(-23, "Mật khẩu chỉ chứa a-z, A-Z, 0-9, 8 đến 30 ký tự"),
    USER_VERIFIED(-24, "Người dùng chưa được xác minh"),
    USERNAME_NOT_EMPTY(-25, "Tên người dùng không trống"),
    PASSWORD_NOT_EMPTY(-26, "Mật khẩu không trống"),
    INVALID_EMAIL(-27, "Email không hợp lệ"),
    INVALID_USERNAME(-28, "Tên người dùng không hợp lệ"),
    USERNAME_IN_USE(-29, "Tên tài khoản đã được sử dụng"),
    EMAIL_IN_USE(-30, "Email đã được sử dụng"),
    EMAIL_REWARD_SUBJECT_INVALID(-31, "Chủ đề không hợp lệ"),
    EMAIL_REWARD_CONTENT_INVALID(-32, "Nội dung không hợp lệ"),
    GET_OBJECT_REDIS_FAIL(-33, "Lấy lại đối tượng bị lỗi"),
    ERROR_SEND_MAIL(-34, "Lỗi gửi mail"),
    ACCOUNT_NOT_EXIST(-35, "Tài khoản không tồn tại"),
    PHONE_NUMBER_ALREADY_USE(-37, "Số điện thoại đã được sử dụng"),
    DISK_NOT_EXIST(-38, "Món ăn không có trong thực đơn"),
    DISK_EXIST(-39, "Món đã có trong thực đơn"),
    ACCOUNT_WAITING_CONFIRMATION(-39, "Tài khoản của bạn đang chờ xác nhận hoặc đã bị khoá"),
    MENU_NULL(-40, "Thực đơn đang rỗng"),
    MAX_SIZE_MENU(-41, "Không thể thêm món ăn vào thực đơn"),
    NOT_ENOUGH_CONDITION_MENU(-42, "Menu chưa đủ điều kiện"),
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
