package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalDimensionalInspectionSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Dimensional Inspection Sample
 */
@Repository
public interface FinalDimensionalInspectionSampleRepository extends JpaRepository<FinalDimensionalInspectionSample, Long> {

    /**
     * Find all samples for a given parent inspection.
     */
    List<FinalDimensionalInspectionSample> findByFinalDimensionalInspectionId(Long parentId);

    /**
     * Find samples by parent ID and sampling number.
     */
    List<FinalDimensionalInspectionSample> findByFinalDimensionalInspectionIdAndSamplingNo(
            Long parentId, Integer samplingNo);

    /**
     * Delete all samples for a given parent inspection.
     */
    void deleteByFinalDimensionalInspectionId(Long parentId);
}

