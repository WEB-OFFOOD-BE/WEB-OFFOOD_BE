package com.web.offood.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionBaseRequest<T> {
  private String action;
  private UUID characterUId;
  private T paramObject;
}
