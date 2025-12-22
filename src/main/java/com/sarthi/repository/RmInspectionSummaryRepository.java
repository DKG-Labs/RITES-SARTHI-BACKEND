package com.sarthi.repository;

import com.sarthi.entity.RmInspectionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for RmInspectionSummary entity.
 */
@Repository
public interface RmInspectionSummaryRepository extends JpaRepository<RmInspectionSummary, Long> {

    Optional<RmInspectionSummary> findByInspectionCallNo(String inspectionCallNo);

    boolean existsByInspectionCallNo(String inspectionCallNo);
}

