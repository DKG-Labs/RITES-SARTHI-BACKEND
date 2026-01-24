package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalMicrostructureTestRequest;
import com.sarthi.dto.finalmaterial.FinalMicrostructureTestResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Inspection - Microstructure Test
 */
public interface FinalMicrostructureTestService {

    /**
     * Save or update microstructure test with samples.
     * Supports both first save (create) and subsequent saves (pause/resume).
     */
    FinalMicrostructureTestResponse saveOrUpdateMicrostructureTest(
            FinalMicrostructureTestRequest request, String userId);

    /**
     * Get microstructure test by ID.
     */
    Optional<FinalMicrostructureTestResponse> getMicrostructureTestById(Long id);

    /**
     * Get all microstructure tests for a given inspection call number.
     */
    List<FinalMicrostructureTestResponse> getMicrostructureTestByCallNo(String inspectionCallNo);

    /**
     * Get all microstructure tests for a given lot number.
     */
    List<FinalMicrostructureTestResponse> getMicrostructureTestByLotNo(String lotNo);

    /**
     * Get microstructure test by call, lot, and heat number.
     */
    Optional<FinalMicrostructureTestResponse> getMicrostructureTestByCallLotHeat(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Update status of microstructure test.
     */
    FinalMicrostructureTestResponse updateStatus(Long id, String status, String userId);

    /**
     * Delete microstructure test and all its samples.
     */
    void deleteMicrostructureTest(Long id);
}

