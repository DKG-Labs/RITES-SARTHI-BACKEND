package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalMicrostructureTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Microstructure Test
 *
 * Supports one inspection session per (inspectionCallNo, lotNo, heatNo) combination.
 */
@Repository
public interface FinalMicrostructureTestRepository extends JpaRepository<FinalMicrostructureTest, Long> {

    /**
     * Find inspection by inspection call number, lot number, and heat number.
     * Since the combination is unique, this returns at most one record.
     */
    Optional<FinalMicrostructureTest> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Find all inspections for a given inspection call number.
     */
    List<FinalMicrostructureTest> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find all inspections for a given lot number.
     */
    List<FinalMicrostructureTest> findByLotNo(String lotNo);

    /**
     * Find all inspections for a given inspection call number and lot number.
     */
    List<FinalMicrostructureTest> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find all inspections for a given heat number.
     */
    List<FinalMicrostructureTest> findByHeatNo(String heatNo);

    /**
     * Find all inspections with a specific status.
     */
    List<FinalMicrostructureTest> findByStatus(String status);

    /**
     * Count inspections by status.
     */
    @Query("SELECT COUNT(f) FROM FinalMicrostructureTest f WHERE f.status = :status")
    long countByStatus(@Param("status") String status);
}

