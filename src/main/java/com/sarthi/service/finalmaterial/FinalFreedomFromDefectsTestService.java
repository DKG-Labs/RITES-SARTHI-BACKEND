package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalFreedomFromDefectsTestRequest;
import com.sarthi.dto.finalmaterial.FinalFreedomFromDefectsTestResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Inspection - Freedom from Defects Test
 */
public interface FinalFreedomFromDefectsTestService {

    /**
     * Save or update freedom from defects test with samples.
     * Supports both first save (create) and subsequent saves (pause/resume).
     */
    FinalFreedomFromDefectsTestResponse saveOrUpdateFreedomFromDefectsTest(
            FinalFreedomFromDefectsTestRequest request, String userId);

    /**
     * Get freedom from defects test by ID.
     */
    Optional<FinalFreedomFromDefectsTestResponse> getFreedomFromDefectsTestById(Long id);

    /**
     * Get all freedom from defects tests for a given inspection call number.
     */
    List<FinalFreedomFromDefectsTestResponse> getFreedomFromDefectsTestByCallNo(String inspectionCallNo);

    /**
     * Get all freedom from defects tests for a given lot number.
     */
    List<FinalFreedomFromDefectsTestResponse> getFreedomFromDefectsTestByLotNo(String lotNo);

    /**
     * Get freedom from defects test by call, lot, and heat number.
     */
    Optional<FinalFreedomFromDefectsTestResponse> getFreedomFromDefectsTestByCallLotHeat(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Update status of freedom from defects test.
     */
    FinalFreedomFromDefectsTestResponse updateStatus(Long id, String status, String userId);

    /**
     * Delete freedom from defects test and all its samples.
     */
    void deleteFreedomFromDefectsTest(Long id);
}

