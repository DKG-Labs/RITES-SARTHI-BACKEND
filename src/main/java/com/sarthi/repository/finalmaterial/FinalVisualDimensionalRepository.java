package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalVisualDimensional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Visual & Dimensional
 */
@Repository
public interface FinalVisualDimensionalRepository extends JpaRepository<FinalVisualDimensional, Long> {

    /**
     * Find all visual dimensional data by inspection call number
     */
    List<FinalVisualDimensional> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find visual dimensional data by inspection call number and lot number
     */
    List<FinalVisualDimensional> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find visual dimensional data by inspection call number and heat number
     */
    List<FinalVisualDimensional> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find visual dimensional data by inspection call number, lot number and heat number
     */
    List<FinalVisualDimensional> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

