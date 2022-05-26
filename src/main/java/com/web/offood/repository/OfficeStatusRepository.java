package com.web.offood.repository;

import com.web.offood.entity.office.OfficeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeStatusRepository extends JpaRepository<OfficeStatus, Integer> {
}
