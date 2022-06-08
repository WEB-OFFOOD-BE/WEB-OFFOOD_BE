package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.constant.status.RestaurantStatus;
import com.web.offood.dto.restaurant.*;
import com.web.offood.entity.restaurant.DiskInfo;
import com.web.offood.entity.restaurant.MenuDetail;
import com.web.offood.entity.restaurant.RestaurantInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.BaseService;
import com.web.offood.service.RestaurantService;
import com.web.offood.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl extends BaseService implements RestaurantService {

    @Override
    public RestaurantDetailResponse getRestaurantDetail(int restaurantId) {

        var restaurantInfo = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var account = accountRepository.findById(restaurantInfo.getAccountId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var menu = menuRepository.findAllByRestaurantId(restaurantId);

        RestaurantDetailResponse response = RestaurantDetailResponse.builder()
                .address(restaurantInfo.getAddress())
                .id(restaurantInfo.getId())
                .email(account.getEmail())
                .description(restaurantInfo.getDescription())
                .address(restaurantInfo.getAddress())
                .avatarUrl(restaurantInfo.getAvatarUrl())
                .menus(menu)
                .build();

        return response;
    }

    @Override
    public String updateRestaurant(RestaurantUpdateRequest request) {
        var restaurantInfo = restaurantRepository.findById(request.getId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));

        restaurantInfo = RestaurantInfo.builder()
                .address(request.getAddress())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .description(request.getDescription())
                .build();
        restaurantRepository.save(restaurantInfo);

        return "OK";
    }

    @Override
    public String changeAvatar(ChangeAvatarRequest request) {
        var restaurantInfo = restaurantRepository.findById(request.getId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));

        restaurantInfo = RestaurantInfo.builder()
                .avatarUrl(request.getAvatar())
                .build();
        restaurantRepository.save(restaurantInfo);
        return "OK";
    }

//    @Override
//    public MenuDetailResponse getMenuDetail(Integer id) {
//        var menu = menuRepository.findById(id).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
//        var disks = diskInfoRepository.findAllByMenuId(id);
//        MenuDetailResponse response = MenuDetailResponse.builder()
//                .id(menu.getId())
//                .diskInfos(disks)
//                .build();
//        return response;
//    }

    @Override
    public String unselectedDisk(ChooseDiskRequest request) {
        var disk = menuDetailRepository.findByMenuIdAndDiskId(request.getMenuId(), request.getDiskId());
        if (disk == null) {
            throw new ApiException(ApiErrorCode.DISK_NOT_EXIST);
        }
        menuDetailRepository.delete(disk);
        return "OK";
    }

    @Override
    public String selectedDisk(ChooseDiskRequest request) {
        var disk = menuDetailRepository.findByMenuIdAndDiskId(request.getMenuId(), request.getDiskId());
        if (disk != null) {
            throw new ApiException(ApiErrorCode.DISK_EXIST);
        }
        disk = MenuDetail.builder()
                .diskId(request.getDiskId())
                .menuId(request.getMenuId())
                .build();
        menuDetailRepository.save(disk);
        return "OK";
    }

    public List<DiskInfo> getAllDisks() {
        return diskInfoRepository.findAll();
    }

    public String updateDisk(updateDiskRequest request) {
        request.validate();
        var disk = diskInfoRepository.findById(request.getId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        disk = DiskInfo.builder()
                .foodName(request.getName())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .updateDate(TimeUtils.convertToTimestamp())
                .description(request.getDescription())
                .build();
        diskInfoRepository.save(disk);
        return "OK";
    }

    public List<RestaurantInfo> getAllRestaurantActivated() {
        return restaurantRepository.findByStatusId(RestaurantStatus.ACTIVE.getValue());
    }

    public List<RestaurantInfo> getAllRestaurantWaitingConfirmation() {
        return restaurantRepository.findByStatusId(RestaurantStatus.WAITING_CONFIRMATION.getValue());
    }

    public List<RestaurantInfo> getAllRestaurantLock() {
        return restaurantRepository.findByStatusId(RestaurantStatus.LOCK.getValue());
    }

    public List<RestaurantInfo> getAllRestaurantUnverified() {
        return restaurantRepository.findByStatusId(RestaurantStatus.ACTIVE_BUT_UNVERIFIED.getValue());
    }

    public List<RestaurantInfo> getAllRestaurant() {
        return restaurantRepository.findAll();
    }


}
