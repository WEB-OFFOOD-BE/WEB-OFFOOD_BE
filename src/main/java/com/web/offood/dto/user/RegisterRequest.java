package com.web.offood.dto.user;

import com.web.offood.entity.account.AccountRoles;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
  private String email;
  private String password;
  private String username;
  List<AccountRoles> accountRoles;

  private String name;

  public void validate(){
    if ( email==null || password == null || username == null || accountRoles == null || name == null)
      throw new ApiException(ApiErrorCode.INPUT_INVALID);
  }
}
