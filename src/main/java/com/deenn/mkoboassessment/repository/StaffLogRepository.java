package com.deenn.mkoboassessment.repository;

import com.deenn.mkoboassessment.entity.StaffLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffLogRepository extends JpaRepository<StaffLog, Long> {
}
