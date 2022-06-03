package com.web.offood.repository;

import com.web.offood.entity.restaurant.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurantIdAndIsActive(Integer restaurantId, Integer isActive);
}
