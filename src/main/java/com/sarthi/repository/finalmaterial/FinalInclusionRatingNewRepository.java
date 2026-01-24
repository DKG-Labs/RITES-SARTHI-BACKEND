package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalInclusionRatingNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Final Inspection - Inclusion Rating (New Structure)
 *
 * Supports one inspection session per (inspectionCallNo, lotNo, heatNo) combination.
 */
@Repository
public interface FinalInclusionRatingNewRepository extends JpaRepository<FinalInclusionRatingNew, Long> {

    /**
     * Find inspection by inspection call number, lot number, and heat number.
     * Since the combination is unique, this returns at most one record.
     */
    Optional<FinalInclusionRatingNew> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Find all inspections for a given inspection call number.
     */
    List<FinalInclusionRatingNew> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find all inspections for a given lot number.
     */
    List<FinalInclusionRatingNew> findByLotNo(String lotNo);

    /**
     * Find all inspections for a given inspection call number and lot number.
     */
    List<FinalInclusionRatingNew> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find all inspections for a given heat number.
     */
    List<FinalInclusionRatingNew> findByHeatNo(String heatNo);

    /**
     * Find all inspections with a specific status.
     */
    List<FinalInclusionRatingNew> findByStatus(String status);

    /**
     * Count inspections by status.
     */
    @Query("SELECT COUNT(f) FROM FinalInclusionRatingNew f WHERE f.status = :status")
    long countByStatus(@Param("status") String status);
}

