package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalVisualInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Visual Inspection
 * Handles database operations for visual inspection data
 */
@Repository
public interface FinalVisualInspectionRepository extends JpaRepository<FinalVisualInspection, Long> {

    /**
     * Find all visual inspection data by inspection call number
     */
    List<FinalVisualInspection> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find visual inspection data by inspection call number and lot number
     */
    Optional<FinalVisualInspection> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find visual inspection data by inspection call number and heat number
     */
    List<FinalVisualInspection> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find visual inspection data by inspection call number, lot number and heat number
     */
    Optional<FinalVisualInspection> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

