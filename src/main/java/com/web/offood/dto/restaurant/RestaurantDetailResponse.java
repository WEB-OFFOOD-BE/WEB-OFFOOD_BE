package com.web.offood.dto.restaurant;

import com.web.offood.entity.restaurant.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDetailResponse {

    public Integer id;
    public String name;
    public String address;
    public String description;
    public String email;
    public String avatarUrl;

    public List<Menu> menus;
}
