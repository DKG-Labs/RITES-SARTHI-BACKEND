package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalDepthOfDecarburization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Depth of Decarburization
 *
 * Supports one inspection session per (inspectionCallNo, heatNo) combination.
 */
@Repository
public interface FinalDepthOfDecarburizationRepository extends JpaRepository<FinalDepthOfDecarburization, Long> {

    /**
     * Find inspection by inspection call number, lot number, and heat number.
     * This combination is unique per lot, supporting multiple lots per heat.
     */
    Optional<FinalDepthOfDecarburization> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Find inspection by inspection call number and heat number.
     * Since the combination is unique, this returns at most one record.
     * @deprecated Use findByInspectionCallNoAndLotNoAndHeatNo instead to support multiple lots per heat
     */
    @Deprecated
    Optional<FinalDepthOfDecarburization> findByInspectionCallNoAndHeatNo(
            String inspectionCallNo, String heatNo);

    /**
     * Find all inspections for a given inspection call number.
     */
    List<FinalDepthOfDecarburization> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find all inspections for a given heat number.
     */
    List<FinalDepthOfDecarburization> findByHeatNo(String heatNo);

    /**
     * Find all inspections with a specific status.
     */
    List<FinalDepthOfDecarburization> findByStatus(String status);

    /**
     * Count inspections by status.
     */
    @Query("SELECT COUNT(f) FROM FinalDepthOfDecarburization f WHERE f.status = :status")
    long countByStatus(@Param("status") String status);
}

