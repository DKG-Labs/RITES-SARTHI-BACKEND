package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalInclusionRatingNewRequest;
import com.sarthi.dto.finalmaterial.FinalInclusionRatingNewResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Inspection - Inclusion Rating (New Structure)
 */
public interface FinalInclusionRatingNewService {

    /**
     * Save or update inclusion rating test with samples.
     * Supports both first save (create) and subsequent saves (pause/resume).
     */
    FinalInclusionRatingNewResponse saveOrUpdateInclusionRating(
            FinalInclusionRatingNewRequest request, String userId);

    /**
     * Get inclusion rating test by ID.
     */
    Optional<FinalInclusionRatingNewResponse> getInclusionRatingById(Long id);

    /**
     * Get all inclusion rating tests for a given inspection call number.
     */
    List<FinalInclusionRatingNewResponse> getInclusionRatingByCallNo(String inspectionCallNo);

    /**
     * Get all inclusion rating tests for a given lot number.
     */
    List<FinalInclusionRatingNewResponse> getInclusionRatingByLotNo(String lotNo);

    /**
     * Get inclusion rating test by call, lot, and heat number.
     */
    Optional<FinalInclusionRatingNewResponse> getInclusionRatingByCallLotHeat(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Update status of inclusion rating test.
     */
    FinalInclusionRatingNewResponse updateStatus(Long id, String status, String userId);

    /**
     * Delete inclusion rating test and all its samples.
     */
    void deleteInclusionRating(Long id);
}

