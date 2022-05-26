package com.web.offood.repository;

import com.web.offood.entity.restaurant.RestaurantDebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDebtRepository extends JpaRepository<RestaurantDebt, Integer> {
}
