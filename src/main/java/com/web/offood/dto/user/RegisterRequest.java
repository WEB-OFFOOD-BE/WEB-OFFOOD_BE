package com.web.offood.dto.user;

import com.web.offood.entity.account.AccountRoles;
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
}
