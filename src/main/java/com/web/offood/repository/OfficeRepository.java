package com.web.offood.repository;

import com.web.offood.entity.office.OfficeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeInfo, Integer> {

    OfficeInfo findByAccountIdAndStatusId(Integer accountId, Integer statusId);

    List<OfficeInfo> findAllByStatusId(Integer statusId);
}
