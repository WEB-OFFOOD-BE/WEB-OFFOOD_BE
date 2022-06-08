package com.web.offood.repository;

import com.web.offood.entity.restaurant.DiskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskInfoRepository extends JpaRepository<DiskInfo, Integer> {
//    List<DiskInfo> findAllByMenuId(Integer menuId);
}
