package com.web.offood.dto.base;

public class ResponseBuilder implements Response {
  private int errorCode;
  private String message;
  private Object data;

  public ResponseBuilder() {
    errorCode = 1;
    message = "Success";
  }

  public static Response ok() {
    return new ResponseBuilder();
  }

  @Override
  public Response with(int errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
    this.data = "";
    return this;
  }

  @Override
  public Response with(Object data) {
    if (data == null) {
      this.data = "";
    } else {
      this.data = data;
    }
    return this;
  }

  @Override
  public <T> BaseResponse<T> build() {
    return new BaseResponse<T>(errorCode, message, (T) data);
  }
}
