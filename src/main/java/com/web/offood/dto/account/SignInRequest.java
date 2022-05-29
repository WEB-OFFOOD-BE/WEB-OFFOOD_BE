package com.web.offood.dto.account;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

  private String username;

  private String password;

  public void validSignIn() {
    if (username == null || username.equals("")) throw new ApiException(ApiErrorCode.INVALID_USERNAME);
    if (password == null || password.equals("")) throw new ApiException(ApiErrorCode.PASSWORD_NOT_EMPTY);
  }
}
