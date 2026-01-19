package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalInspectionLotResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repository for Final Inspection Lot Results
 */
@Repository
public interface FinalInspectionLotResultsRepository extends JpaRepository<FinalInspectionLotResults, Long> {

    /**
     * Find lot results by inspection call number and lot number
     */
    Optional<FinalInspectionLotResults> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find all lot results for an inspection call
     */
    List<FinalInspectionLotResults> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find lot results by lot number
     */
    List<FinalInspectionLotResults> findByLotNo(String lotNo);

    /**
     * Check if lot results exist
     */
    boolean existsByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);
}

