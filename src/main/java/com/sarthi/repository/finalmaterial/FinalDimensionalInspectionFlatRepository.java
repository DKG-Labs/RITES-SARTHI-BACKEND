package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalDimensionalInspectionFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Dimensional Inspection - FLAT STRUCTURE
 */
@Repository
public interface FinalDimensionalInspectionFlatRepository extends JpaRepository<FinalDimensionalInspectionFlat, Long> {

    /**
     * Find by unique combination (inspectionCallNo, lotNo, heatNo)
     */
    Optional<FinalDimensionalInspectionFlat> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Find by inspection call number
     */
    List<FinalDimensionalInspectionFlat> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find by call number and lot number
     */
    Optional<FinalDimensionalInspectionFlat> findByInspectionCallNoAndLotNo(
            String inspectionCallNo, String lotNo);

    /**
     * Find by call number and heat number
     */
    List<FinalDimensionalInspectionFlat> findByInspectionCallNoAndHeatNo(
            String inspectionCallNo, String heatNo);
}

