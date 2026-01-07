package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalInspectionLotDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection Lot Details
 */
@Repository
public interface FinalInspectionLotDetailsRepository extends JpaRepository<FinalInspectionLotDetails, Long> {

    /**
     * Find all lot details by Final Detail ID
     */
    List<FinalInspectionLotDetails> findByFinalDetailId(Long finalDetailId);

    /**
     * Find lot details by lot number
     */
    List<FinalInspectionLotDetails> findByLotNumber(String lotNumber);

    /**
     * Find lot details by heat number
     */
    List<FinalInspectionLotDetails> findByHeatNumber(String heatNumber);
}

