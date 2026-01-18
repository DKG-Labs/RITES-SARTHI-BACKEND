package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalHardnessTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Hardness Test
 */
@Repository
public interface FinalHardnessTestRepository extends JpaRepository<FinalHardnessTest, Long> {

    /**
     * Find all hardness test data by inspection call number
     */
    List<FinalHardnessTest> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find hardness test data by inspection call number and lot number
     */
    List<FinalHardnessTest> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find hardness test data by inspection call number and heat number
     */
    List<FinalHardnessTest> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find hardness test data by inspection call number, lot number and heat number
     */
    List<FinalHardnessTest> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

