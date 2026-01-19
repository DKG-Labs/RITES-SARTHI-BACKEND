package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalInspectionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Final Inspection Summary
 */
@Repository
public interface FinalInspectionSummaryRepository extends JpaRepository<FinalInspectionSummary, Long> {

    /**
     * Find inspection summary by inspection call number
     */
    Optional<FinalInspectionSummary> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Check if summary exists for a call
     */
    boolean existsByInspectionCallNo(String inspectionCallNo);
}

