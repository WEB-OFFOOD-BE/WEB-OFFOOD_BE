package com.web.offood.dto.constant.type;

public enum SendEmailType {
  UNKNOWN(0),
  VERIFY_EMAIL(1),
  RESET_PASSWORD(2),
  BASIC_CONTENT(3),
  ;

  private final Integer type;

  SendEmailType(Integer type) {
    this.type = type;
  }

  public Integer getType() {
    return type;
  }
}
