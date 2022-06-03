package com.web.offood.dto.constant.type;

public enum EmailLogType {
  SEND_CODE(0),
  VERIFIED_USER(1),
  FORGOT_PASS(2),
  ;

  private final Integer type;

  EmailLogType(Integer type) {
    this.type = type;
  }

  public Integer getValue() {
    return type;
  }
}
