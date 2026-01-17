package com.sarthi.repository.processmaterial;

import com.sarthi.entity.processmaterial.ProcessInspectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * Find distinct RM IC numbers by Process IC certificate number
     * Joins with inspection_calls and inspection_complete_details
     */
    @Query(value = "SELECT DISTINCT pid.rm_ic_number " +
            "FROM process_inspection_details pid " +
            "INNER JOIN inspection_calls ic ON pid.ic_id = ic.id " +
            "INNER JOIN inspection_complete_details icd ON ic.ic_number = icd.CALL_NO " +
            "WHERE icd.CERTIFICATE_NO = :certificateNo " +
            "AND pid.rm_ic_number IS NOT NULL " +
            "ORDER BY pid.rm_ic_number",
            nativeQuery = true)
    List<String> findRmIcNumbersByCertificateNo(@Param("certificateNo") String certificateNo);

    /**
     * Find distinct lot numbers by RM IC number
     */
    @Query(value = "SELECT DISTINCT lot_number " +
            "FROM process_inspection_details " +
            "WHERE rm_ic_number = :rmIcNumber " +
            "AND lot_number IS NOT NULL " +
            "ORDER BY lot_number",
            nativeQuery = true)
    List<String> findLotNumbersByRmIcNumber(@Param("rmIcNumber") String rmIcNumber);
}

