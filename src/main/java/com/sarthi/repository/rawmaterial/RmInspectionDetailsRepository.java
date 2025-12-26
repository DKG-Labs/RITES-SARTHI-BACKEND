package com.sarthi.repository.rawmaterial;

import com.sarthi.entity.rawmaterial.RmInspectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for RmInspectionDetails entity.
 * Provides CRUD operations and custom queries for RM inspection details.
 * Matches actual database schema: rm_inspection_details table.
 */
@Repository
public interface RmInspectionDetailsRepository extends JpaRepository<RmInspectionDetails, Integer> {

    /* ==================== Find by Inspection Call ID ==================== */

    /**
     * Find RM details by inspection call ID
     */
    @Query("SELECT rd FROM RmInspectionDetails rd WHERE rd.inspectionCall.id = :icId")
    Optional<RmInspectionDetails> findByIcId(@Param("icId") Integer icId);

    /* ==================== Find by TC Number ==================== */

    Optional<RmInspectionDetails> findByTcNumber(String tcNumber);

    /* ==================== Find by Manufacturer ==================== */

    List<RmInspectionDetails> findByManufacturerContainingIgnoreCase(String manufacturer);

    /* ==================== Find with Heat Quantities ==================== */

    /**
     * Find RM details with heat quantities
     */
    @Query("SELECT DISTINCT rd FROM RmInspectionDetails rd " +
           "LEFT JOIN FETCH rd.rmHeatQuantities " +
           "WHERE rd.id = :id")
    Optional<RmInspectionDetails> findByIdWithHeatQuantities(@Param("id") Integer id);

    /**
     * Find RM details with parent inspection call
     */
    @Query("SELECT rd FROM RmInspectionDetails rd " +
           "JOIN FETCH rd.inspectionCall " +
           "WHERE rd.id = :id")
    Optional<RmInspectionDetails> findByIdWithInspectionCall(@Param("id") Integer id);
}

