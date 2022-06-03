package com.web.offood.repository;

import com.web.offood.entity.restaurant.MenuDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDetailRepository extends JpaRepository<MenuDetail, Integer> {
    MenuDetail findByMenuIdAndDiskId(Integer menuId, Integer diskId);
}
