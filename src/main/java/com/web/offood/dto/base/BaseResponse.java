package com.web.offood.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    @JsonProperty("code") //  1  success   0 error dau vao   -1 -xxxxxx
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;
}