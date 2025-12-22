package com.sarthi.repository;

import com.sarthi.entity.RmHeatTcMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RmHeatTcMapping entity.
 * Provides data access methods for heat-TC mapping operations.
 */
@Repository
public interface RmHeatTcMappingRepository extends JpaRepository<RmHeatTcMapping, Long> {

    /**
     * Find all heat TC mappings by inspection request ID
     */
    List<RmHeatTcMapping> findByInspectionRequestId(Long inspectionRequestId);

    /**
     * Find heat TC mapping by heat number
     */
    List<RmHeatTcMapping> findByHeatNumber(String heatNumber);

    /**
     * Find heat TC mapping by TC number
     */
    List<RmHeatTcMapping> findByTcNumber(String tcNumber);

    /**
     * Find heat TC mappings by manufacturer
     */
    List<RmHeatTcMapping> findByManufacturer(String manufacturer);

    /**
     * Delete all mappings for a specific inspection request
     */
    void deleteByInspectionRequestId(Long inspectionRequestId);

    /**
     * Count heat TC mappings for a specific inspection request
     */
    @Query("SELECT COUNT(h) FROM RmHeatTcMapping h WHERE h.inspectionRequest.id = :requestId")
    long countByInspectionRequestId(@Param("requestId") Long requestId);
}

