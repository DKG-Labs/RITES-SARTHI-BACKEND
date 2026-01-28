package com.sarthi.repository.rawmaterial;

import com.sarthi.dto.InspectionDataDto;
import com.sarthi.entity.rawmaterial.InspectionCall;
import org.hibernate.query.SelectionQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InspectionCall entity.
 * Provides CRUD operations and custom queries for inspection calls.
 */
@Repository
public interface InspectionCallRepository extends JpaRepository<InspectionCall, Integer> {

    /* ==================== Find by IC Number ==================== */

    Optional<InspectionCall> findByIcNumber(String icNumber);

    /**
     * Batch fetch inspection calls by IC numbers (for performance optimization)
     */
    List<InspectionCall> findByIcNumberIn(List<String> icNumbers);

    /* ==================== Find by Status ==================== */

    List<InspectionCall> findByStatusIgnoreCaseOrderByCreatedAtDesc(String status);

    List<InspectionCall> findByStatusInOrderByCreatedAtDesc(List<String> statuses);

    /* ==================== Find by Type of Call ==================== */

    List<InspectionCall> findByTypeOfCallOrderByCreatedAtDesc(String typeOfCall);

    List<InspectionCall> findByTypeOfCallAndStatusIgnoreCaseOrderByCreatedAtDesc(
            String typeOfCall, String status);

    /* ==================== Find by PO Number ==================== */

    List<InspectionCall> findByPoNoOrderByCreatedAtDesc(String poNo);

    /* ==================== Find by Company ==================== */

    List<InspectionCall> findByCompanyNameContainingIgnoreCaseOrderByCreatedAtDesc(String companyName);

    /* ==================== Find by Vendor ID ==================== */

    List<InspectionCall> findByVendorIdOrderByCreatedAtDesc(String vendorId);

    /* ==================== Custom Queries ==================== */

    /**
     * Find all Raw Material inspection calls with details eagerly loaded
     */
    @Query("SELECT DISTINCT ic FROM InspectionCall ic " +
           "LEFT JOIN FETCH ic.rmInspectionDetails " +
           "WHERE ic.typeOfCall = 'Raw Material' " +
           "ORDER BY ic.createdAt DESC")
    List<InspectionCall> findAllRawMaterialCallsWithDetails();

    /**
     * Find Raw Material calls by status with details
     */
    @Query("SELECT DISTINCT ic FROM InspectionCall ic " +
           "LEFT JOIN FETCH ic.rmInspectionDetails " +
           "WHERE ic.typeOfCall = 'Raw Material' AND UPPER(ic.status) = UPPER(:status) " +
           "ORDER BY ic.createdAt DESC")
    List<InspectionCall> findRawMaterialCallsByStatusWithDetails(@Param("status") String status);

    /**
     * Count calls by status and type
     */
    @Query("SELECT COUNT(ic) FROM InspectionCall ic " +
           "WHERE ic.typeOfCall = :type AND UPPER(ic.status) = UPPER(:status)")
    long countByTypeAndStatus(@Param("type") String type, @Param("status") String status);

    /**
     * Count calls by type
     */
    @Query("SELECT COUNT(ic) FROM InspectionCall ic WHERE ic.typeOfCall = :type")
    long countByTypeOfCall(@Param("type") String type);

    /**
     * Count calls by type and created date (for daily sequence)
     * Counts ICs created on a specific date for a given type
     */
    @Query("SELECT COUNT(ic) FROM InspectionCall ic " +
           "WHERE ic.typeOfCall = :type " +
           "AND FUNCTION('DATE', ic.createdAt) = :date")
    long countByTypeOfCallAndCreatedDate(@Param("type") String type, @Param("date") java.time.LocalDate date);

    /**
     * Check if IC number exists
     */
    boolean existsByIcNumber(String icNumber);

    @Query("""
SELECT new com.sarthi.dto.InspectionDataDto(
    ic.icNumber,
    ic.poNo,
    ic.vendorId,
    ic.typeOfCall,
    ic.desiredInspectionDate,
    ic.placeOfInspection
)
FROM InspectionCall ic
WHERE ic.icNumber IN :icNumbers
""")
    List<InspectionDataDto> findLiteByIcNumberIn(
            @Param("icNumbers") List<String> icNumbers
    );


    @Query("SELECT ic.icNumber FROM InspectionCall ic WHERE ic.poSerialNo = :poSerialNo")
    List<String> findCallNumbersByPoNo(@Param("poSerialNo") String poSerialNo);

}

