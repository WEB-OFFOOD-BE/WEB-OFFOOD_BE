package com.web.offood.dto.constant;

public enum ValidateType {
  NORMAL(""),
  GLOBALOBJINOBJ(ValidateType.GLOBAL_OBJECT_IN_OBJECT),
  PRIVATEOBJINOBJ(ValidateType.PRIVATE_OBJECT_IN_OBJECT),
  SHOWINTITLE(ValidateType.SHOW_IN_TITLE);

  private final String value;

  public static final String GLOBAL_OBJECT_IN_OBJECT = "GlobalObjectInObject";
  public static final String PRIVATE_OBJECT_IN_OBJECT = "PrivateObjectInObject";
  public static final String SHOW_IN_TITLE = "ShowInTitle";

  ValidateType(String value) {
    this.value = value;
  }

  public final String getValue() {
    return this.value;
  }
}
