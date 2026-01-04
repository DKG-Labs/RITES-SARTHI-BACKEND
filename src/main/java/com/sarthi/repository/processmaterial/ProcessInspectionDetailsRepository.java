package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessInspectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessInspectionDetailsRepository extends JpaRepository<ProcessInspectionDetails, Long> {

    /**
     * Find Process Inspection Details by Inspection Call ID
     */
    @Query("SELECT pd FROM ProcessInspectionDetails pd WHERE pd.inspectionCall.id = :icId")
    Optional<ProcessInspectionDetails> findByIcId(@Param("icId") Long icId);

    /**
     * Find Process Inspection Details by RM IC Number
     */
    Optional<ProcessInspectionDetails> findByRmIcNumber(String rmIcNumber);

    /**
     * Find Process Inspection Details by Lot Number
     */
    Optional<ProcessInspectionDetails> findByLotNumber(String lotNumber);
}

