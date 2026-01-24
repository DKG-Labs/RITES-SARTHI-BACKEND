package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalApplicationDeflectionSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Final Inspection - Application & Deflection Test Sample
 */
@Repository
public interface FinalApplicationDeflectionSampleRepository extends JpaRepository<FinalApplicationDeflectionSample, Long> {

    /**
     * Find all samples for a given parent inspection.
     */
    List<FinalApplicationDeflectionSample> findByFinalApplicationDeflectionId(Long parentId);

    /**
     * Find samples by parent ID and sampling number.
     */
    List<FinalApplicationDeflectionSample> findByFinalApplicationDeflectionIdAndSamplingNo(
            Long parentId, Integer samplingNo);

    /**
     * Delete all samples for a given parent inspection.
     */
    void deleteByFinalApplicationDeflectionId(Long parentId);
}

