package com.web.offood.repository;

import com.web.offood.entity.restaurant.DiskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskTypeRepository extends JpaRepository<DiskType, Integer> {
}
