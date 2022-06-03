package com.web.offood.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantUpdateRequest {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String address;
    private String description;
}