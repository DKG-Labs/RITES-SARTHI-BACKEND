package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalDimensionalInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Dimensional Inspection
 * Handles database operations for dimensional inspection data
 */
@Repository
public interface FinalDimensionalInspectionRepository extends JpaRepository<FinalDimensionalInspection, Long> {

    /**
     * Find all dimensional inspection data by inspection call number
     */
    List<FinalDimensionalInspection> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find dimensional inspection data by inspection call number and lot number
     */
    Optional<FinalDimensionalInspection> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find dimensional inspection data by inspection call number and heat number
     */
    List<FinalDimensionalInspection> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find dimensional inspection data by inspection call number, lot number and heat number
     */
    Optional<FinalDimensionalInspection> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

