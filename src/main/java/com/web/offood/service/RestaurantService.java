package com.web.offood.service;

import com.web.offood.dto.restaurant.*;
import org.springframework.stereotype.Service;

@Service
public interface RestaurantService {
    RestaurantDetailResponse getRestaurantDetail(int restaurantId);

    String updateRestaurant(RestaurantUpdateRequest request);

    String changeAvatar(ChangeAvatarRequest request);

    MenuDetailResponse getMenuDetail(Integer id);

    String unselectedDisk(ChooseDiskRequest request);

    String selectedDisk(ChooseDiskRequest request);
}
