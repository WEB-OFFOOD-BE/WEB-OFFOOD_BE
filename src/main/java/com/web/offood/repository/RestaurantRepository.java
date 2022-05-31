package com.web.offood.repository;

import com.web.offood.entity.restaurant.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantInfo, Integer> {

    RestaurantInfo findByAccountIdAndStatusId(Integer accountId, Integer statusId);

}
