package com.web.offood.service;

import com.web.offood.dto.restaurant.*;
import com.web.offood.entity.restaurant.DiskInfo;
import com.web.offood.entity.restaurant.RestaurantInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantDetailResponse getRestaurantDetail(int restaurantId);

    String updateRestaurant(RestaurantUpdateRequest request);

    String changeAvatar(ChangeAvatarRequest request);

    String unselectedDisk(ChooseDiskRequest request);

    String selectedDisk(ChooseDiskRequest request);

    String updateDisk(updateDiskRequest request);

    List<RestaurantInfo> getAllRestaurantActivated();

    List<RestaurantInfo> getAllRestaurantWaitingConfirmation();

    List<RestaurantInfo> getAllRestaurantLock();

    List<RestaurantInfo> getAllRestaurantUnverified();

    List<RestaurantInfo> getAllRestaurant();

    String createOrUpdateDisk(DiskInfoRequest infoRequest);

    List<DiskInfo> getDisksByMenu(Integer menuId);

    String deleteDiskInMenu(MenuIdRequest request);

    String addDiskOnMenu(AddDiskOnMenuRequest request);
}
