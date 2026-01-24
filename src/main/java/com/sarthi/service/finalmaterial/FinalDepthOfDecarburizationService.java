package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalDepthOfDecarburizationRequest;
import com.sarthi.dto.finalmaterial.FinalDepthOfDecarburizationResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Inspection - Depth of Decarburization
 */
public interface FinalDepthOfDecarburizationService {

    /**
     * Save or update depth of decarburization test with samples.
     * Supports both first save (create) and subsequent saves (pause/resume).
     */
    FinalDepthOfDecarburizationResponse saveOrUpdateDepthOfDecarburization(
            FinalDepthOfDecarburizationRequest request, String userId);

    /**
     * Get depth of decarburization test by ID.
     */
    Optional<FinalDepthOfDecarburizationResponse> getDepthOfDecarburizationById(Long id);

    /**
     * Get all depth of decarburization tests for a given inspection call number.
     */
    List<FinalDepthOfDecarburizationResponse> getDepthOfDecarburizationByCallNo(String inspectionCallNo);

    /**
     * Get all depth of decarburization tests for a given heat number.
     */
    List<FinalDepthOfDecarburizationResponse> getDepthOfDecarburizationByHeatNo(String heatNo);

    /**
     * Update status of depth of decarburization test.
     */
    FinalDepthOfDecarburizationResponse updateStatus(Long id, String status, String userId);

    /**
     * Delete depth of decarburization test and all its samples.
     */
    void deleteDepthOfDecarburization(Long id);
}

