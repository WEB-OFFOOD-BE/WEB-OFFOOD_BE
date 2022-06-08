package com.web.offood.dto.account;

import com.web.offood.entity.restaurant.RestaurantInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponse {
    public String username;
    public String email;
    public String accountRoles;
    public String accessToken;
    public RestaurantInfo restaurantInfo;
}
