package com.deenn.mkoboassessment.repository;

import com.deenn.mkoboassessment.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Query("SELECT p FROM Patient p WHERE p.age >= 2")
    Page<Patient> findAllPatientsWithAgeUpToTwo(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM Patient p WHERE p.created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    int deleteByCreationDateBetween(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

}
