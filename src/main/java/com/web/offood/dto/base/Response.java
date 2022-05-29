package com.web.offood.dto.base;

public interface Response {
  Response with(int status, String message);

  Response with(Object data);

  <T> BaseResponse<T> build();
}
