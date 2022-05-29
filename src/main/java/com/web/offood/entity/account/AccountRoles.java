package com.web.offood.entity.account;

import org.springframework.security.core.GrantedAuthority;

public enum AccountRoles implements GrantedAuthority {
  ROLE_ADMIN, ROLE_RESTAURANT, ROLE_OFFICE;

  public String getAuthority() {
    return name();
  }

}
