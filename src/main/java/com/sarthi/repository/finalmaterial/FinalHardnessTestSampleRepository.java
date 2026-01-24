package com.sarthi.repository.finalmaterial;

import com.sarthi.entity.finalmaterial.FinalHardnessTestSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for FinalHardnessTestSample entity
 */
@Repository
public interface FinalHardnessTestSampleRepository extends JpaRepository<FinalHardnessTestSample, Long> {

    /**
     * Find all samples for a given inspection session.
     */
    List<FinalHardnessTestSample> findByFinalHardnessTestId(Long finalHardnessTestId);

    /**
     * Find all samples for a given inspection session and sampling number.
     */
    List<FinalHardnessTestSample> findByFinalHardnessTestIdAndSamplingNo(
        Long finalHardnessTestId,
        Integer samplingNo
    );

    /**
     * Count rejected samples for a given inspection session.
     */
    @Query("SELECT COUNT(s) FROM FinalHardnessTestSample s WHERE s.finalHardnessTest.id = :testId AND s.isRejected = true")
    long countRejectedByTestId(@Param("testId") Long testId);

    /**
     * Count all samples for a given inspection session.
     */
    @Query("SELECT COUNT(s) FROM FinalHardnessTestSample s WHERE s.finalHardnessTest.id = :testId")
    long countByTestId(@Param("testId") Long testId);

    /**
     * Find the maximum sampling number for a given inspection session.
     */
    @Query("SELECT COALESCE(MAX(s.samplingNo), 0) FROM FinalHardnessTestSample s WHERE s.finalHardnessTest.id = :testId")
    Integer findMaxSamplingNo(@Param("testId") Long testId);

    /**
     * Delete all samples for a given inspection session.
     * Used when updating samples to replace old samples with new ones.
     */
    @Modifying
    @Transactional
    void deleteByFinalHardnessTestId(Long finalHardnessTestId);
}

