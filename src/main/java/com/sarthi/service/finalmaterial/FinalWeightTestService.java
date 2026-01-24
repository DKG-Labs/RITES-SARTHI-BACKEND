package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalWeightTestRequest;
import com.sarthi.dto.finalmaterial.FinalWeightTestResponse;
import com.sarthi.entity.finalmaterial.FinalWeightTest;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Weight Test operations
 * 
 * Handles the two-table design:
 * - Parent table: final_weight_test (one row per inspection session)
 * - Child table: final_weight_test_sample (all sample readings)
 * 
 * Supports pausing and resuming inspections.
 */
public interface FinalWeightTestService {

    /**
     * Save or update a weight test inspection session with samples.
     * 
     * On FIRST save:
     * - Creates new parent row with status = PENDING
     * - Sets created_by, created_at, updated_by, updated_at
     * 
     * On SUBSEQUENT saves (pause/resume/add samples):
     * - Updates existing parent row
     * - Keeps created_by and created_at unchanged
     * - Updates updated_by and updated_at
     * - Adds new sample rows
     * 
     * @param request The weight test request with samples
     * @param userId The current user ID
     * @return The saved inspection session with all samples
     */
    FinalWeightTestResponse saveOrUpdateWeightTest(FinalWeightTestRequest request, String userId);

    /**
     * Get weight test by inspection call, lot, and heat (unique combination).
     */
    Optional<FinalWeightTestResponse> getWeightTest(String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Get all weight tests for an inspection call.
     */
    List<FinalWeightTestResponse> getWeightTestsByCall(String inspectionCallNo);

    /**
     * Get weight test by ID.
     */
    Optional<FinalWeightTestResponse> getWeightTestById(Long id);

    /**
     * Update the status of a weight test (e.g., from PENDING to OK/NOT_OK).
     */
    FinalWeightTestResponse updateStatus(Long id, String status, String userId);

    /**
     * Update remarks for a weight test.
     */
    FinalWeightTestResponse updateRemarks(Long id, String remarks, String userId);

    /**
     * Delete a weight test and all its samples.
     */
    void deleteWeightTest(Long id);
}

