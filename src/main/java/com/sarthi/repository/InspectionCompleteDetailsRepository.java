package com.sarthi.repository;


import com.sarthi.entity.InspectionCompleteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InspectionCompleteDetailsRepository extends JpaRepository<InspectionCompleteDetails, Long> {

    /**
     * Find InspectionCompleteDetails by call number
     */
    Optional<InspectionCompleteDetails> findByCallNo(String callNo);

    /**
     * Find all certificate numbers for Process ICs (EP prefix) filtered by vendor
     * Joins with inspection_calls table to filter by vendor_id
     */
    @Query(value = "SELECT DISTINCT icd.CERTIFICATE_NO " +
            "FROM inspection_complete_details icd " +
            "INNER JOIN inspection_calls ic ON icd.CALL_NO = ic.ic_number " +
            "WHERE icd.CALL_NO LIKE 'EP%' " +
            "AND ic.vendor_id = :vendorId " +
            "ORDER BY icd.CERTIFICATE_NO DESC",
            nativeQuery = true)
    List<String> findProcessIcCertificateNumbersByVendor(@Param("vendorId") String vendorId);

    /**
     * Find all certificate numbers for RM ICs (ER prefix) filtered by PO number
     * Used for Process Inspection Call dropdown to show only RM ICs for the specific PO
     */
    @Query(value = "SELECT DISTINCT icd.CERTIFICATE_NO " +
            "FROM inspection_complete_details icd " +
            "WHERE icd.CALL_NO LIKE 'ER-%' " +
            "AND icd.PO_NO = :poNo " +
            "ORDER BY icd.CERTIFICATE_NO DESC",
            nativeQuery = true)
    List<String> findCompletedRmIcCertificateNumbersByPoNo(@Param("poNo") String poNo);
}
