package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalToeLoadTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Toe Load Test
 */
@Repository
public interface FinalToeLoadTestRepository extends JpaRepository<FinalToeLoadTest, Long> {

    /**
     * Find all toe load test data by inspection call number
     */
    List<FinalToeLoadTest> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find toe load test data by inspection call number and lot number
     */
    List<FinalToeLoadTest> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find toe load test data by inspection call number and heat number
     */
    List<FinalToeLoadTest> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find toe load test data by inspection call number, lot number and heat number
     */
    List<FinalToeLoadTest> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

