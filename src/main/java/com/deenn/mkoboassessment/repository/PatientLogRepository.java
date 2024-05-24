package com.deenn.mkoboassessment.repository;

import com.deenn.mkoboassessment.entity.PatientLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientLogRepository extends JpaRepository<PatientLog, Long> {
}
