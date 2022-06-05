package com.web.offood.repository;

import com.web.offood.entity.restaurant.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantInfo, Integer> {

    RestaurantInfo findByAccountIdAndStatusId(Integer accountId, Integer statusId);

    List<RestaurantInfo> findAllByStatusId(Integer statusId);

    RestaurantInfo findByPhoneNumber(String phoneNumber);

}
