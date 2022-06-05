package com.web.offood.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionBaseRequest<T> {
  private String action;
  private T paramObject;
}
