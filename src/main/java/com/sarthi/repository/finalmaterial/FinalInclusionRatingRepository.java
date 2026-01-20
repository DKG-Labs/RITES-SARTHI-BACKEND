package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalInclusionRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Inclusion & Decarb
 */
@Repository
public interface FinalInclusionRatingRepository extends JpaRepository<FinalInclusionRating, Long> {

    /**
     * Find all inclusion rating data by inspection call number
     */
    List<FinalInclusionRating> findByInspectionCallNo(String inspectionCallNo);

    /**
     * Find inclusion rating data by inspection call number and lot number
     */
    List<FinalInclusionRating> findByInspectionCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Find inclusion rating data by inspection call number and heat number
     */
    List<FinalInclusionRating> findByInspectionCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Find inclusion rating data by inspection call number, lot number and heat number
     */
    List<FinalInclusionRating> findByInspectionCallNoAndLotNoAndHeatNo(
            String inspectionCallNo, String lotNo, String heatNo);
}

