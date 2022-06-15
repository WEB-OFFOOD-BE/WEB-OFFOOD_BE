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

import java.util.ArrayList;
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

    public String createOrUpdateDisk(DiskInfoRequest infoRequest) {
        infoRequest.validate();
        var menu = menuRepository.findById(infoRequest.getMenuId()).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        var disk = diskInfoRepository.findById(infoRequest.getId()).orElse(null);

        if (disk != null) {
            disk.setDescription(infoRequest.getDescription())
                    .setFoodName(infoRequest.getFoodName())
                    .setUpdateDate(TimeUtils.convertToTimestamp())
                    .setImageUrl(infoRequest.getImageUrl())
                    .setPrice(infoRequest.getPrice());
            diskInfoRepository.save(disk);
        } else {
            disk = DiskInfo.builder()
                    .createDate(TimeUtils.convertToTimestamp())
                    .description(infoRequest.getDescription())
                    .foodName(infoRequest.getFoodName())
                    .updateDate(TimeUtils.convertToTimestamp())
                    .imageUrl(infoRequest.getImageUrl())
                    .price(infoRequest.getPrice())
                    .isActive(true)
                    .build();
            diskInfoRepository.save(disk);
            menuDetailRepository.save(MenuDetail.builder()
                    .menuId(menu.getId())
                    .diskId(disk.getId())
                    .is_selected(false)
                    .build());
        }
        return "OK";
    }

    public List<DiskInfo> getDisksByMenu(Integer menuId){
        var menu = menuDetailRepository.findByMenuId(menuId);
        if(menu.size() == 0){
            throw new ApiException(ApiErrorCode.OBJECT_NOT_FOUND);
        }
        List<DiskInfo> diskInfos = new ArrayList<>();
        for (int i = 0; i <= menu.size(); i++){
            var diskInfo = diskInfoRepository.findById(menu.get(i).getDiskId()).orElse(null);
            diskInfos.add(diskInfo);
        }
        return diskInfos;
    }
    public String deleteDiskInMenu(Integer menuId){
        var item = menuDetailRepository.findById(menuId).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        menuDetailRepository.delete(item);
        return "OK";
    }

    public String addDiskOnMenu(Integer menuId, Integer diskId){
        var menu = menuRepository.findById(menuId).orElseThrow(() -> new ApiException(ApiErrorCode.OBJECT_NOT_FOUND));
        if (menuDetailRepository.findByMenuIdAndDiskId(menuId, diskId) != null){
         throw new ApiException(ApiErrorCode.DISK_EXIST);
        }
        //:todo add disk on menu
        return "OK";
    }
}
