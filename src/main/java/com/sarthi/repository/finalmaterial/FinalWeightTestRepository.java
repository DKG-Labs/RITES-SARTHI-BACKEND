package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalWeightTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Weight Test
 */
@Repository
public interface FinalWeightTestRepository extends JpaRepository<FinalWeightTest, Long> {

    /**
     * Find all weight test data by inspection call number
     */
    List<FinalWeightTest> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find weight test data by inspection call number and lot number
     */
    List<FinalWeightTest> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find weight test data by inspection call number and heat number
     */
    List<FinalWeightTest> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find weight test data by inspection call number, lot number and heat number
     */
    List<FinalWeightTest> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

