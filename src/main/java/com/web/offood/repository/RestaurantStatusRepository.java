package com.web.offood.repository;

import com.web.offood.entity.restaurant.RestaurantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantStatusRepository extends JpaRepository<RestaurantStatus, Integer> {
}
