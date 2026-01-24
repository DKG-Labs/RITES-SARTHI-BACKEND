package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalWeightTestSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for FinalWeightTestSample entity
 */
@Repository
public interface FinalWeightTestSampleRepository extends JpaRepository<FinalWeightTestSample, Long> {

    /**
     * Find all samples for a given inspection session.
     */
    List<FinalWeightTestSample> findByFinalWeightTestId(Long finalWeightTestId);

    /**
     * Find all samples for a given inspection session and sampling number.
     */
    List<FinalWeightTestSample> findByFinalWeightTestIdAndSamplingNo(
        Long finalWeightTestId,
        Integer samplingNo
    );

    /**
     * Count rejected samples for a given inspection session.
     */
    @Query("SELECT COUNT(s) FROM FinalWeightTestSample s WHERE s.finalWeightTest.id = :testId AND s.isRejected = true")
    long countRejectedByTestId(@Param("testId") Long testId);

    /**
     * Count all samples for a given inspection session.
     */
    @Query("SELECT COUNT(s) FROM FinalWeightTestSample s WHERE s.finalWeightTest.id = :testId")
    long countByTestId(@Param("testId") Long testId);

    /**
     * Find the maximum sampling number for a given inspection session.
     */
    @Query("SELECT COALESCE(MAX(s.samplingNo), 0) FROM FinalWeightTestSample s WHERE s.finalWeightTest.id = :testId")
    Integer findMaxSamplingNo(@Param("testId") Long testId);

    /**
     * Delete all samples for a given inspection session.
     * Used when updating samples to replace old samples with new ones.
     */
    @Modifying
    @Transactional
    void deleteByFinalWeightTestId(Long finalWeightTestId);
}

