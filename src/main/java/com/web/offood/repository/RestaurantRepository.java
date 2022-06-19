package com.web.offood.repository;

import com.web.offood.entity.restaurant.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantInfo, Integer> {

    RestaurantInfo findByAccountIdAndStatusId(Integer accountId, Integer statusId);

    List<RestaurantInfo> findAllByStatusIdAndIsActive(Integer statusId, Boolean isActive);

    RestaurantInfo findByPhoneNumber(String phoneNumber);

    List<RestaurantInfo> findByStatusId( Integer statusId);

    RestaurantInfo findByAccountId(Integer accountId);

    RestaurantInfo findByIdAndIsActive(Integer id, Boolean isActive);

    List<RestaurantInfo> findAllByIsActive( Boolean isActive);

}
