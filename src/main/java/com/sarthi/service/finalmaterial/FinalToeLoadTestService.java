package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalToeLoadTestRequest;
import com.sarthi.dto.finalmaterial.FinalToeLoadTestResponse;
import com.sarthi.entity.finalmaterial.FinalToeLoadTest;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Toe Load Test operations
 * 
 * Handles the two-table design:
 * - Parent table: final_toe_load_test (one row per inspection session)
 * - Child table: final_toe_load_test_sample (all sample readings)
 * 
 * Supports pausing and resuming inspections.
 */
public interface FinalToeLoadTestService {

    /**
     * Save or update a toe load test inspection session with samples.
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
     * @param request The toe load test request with samples
     * @param userId The current user ID
     * @return The saved inspection session with all samples
     */
    FinalToeLoadTestResponse saveOrUpdateToeLoadTest(FinalToeLoadTestRequest request, String userId);

    /**
     * Get toe load test by inspection call, lot, and heat (unique combination).
     */
    Optional<FinalToeLoadTestResponse> getToeLoadTest(String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Get all toe load tests for an inspection call.
     */
    List<FinalToeLoadTestResponse> getToeLoadTestsByCall(String inspectionCallNo);

    /**
     * Get toe load test by ID.
     */
    Optional<FinalToeLoadTestResponse> getToeLoadTestById(Long id);

    /**
     * Update the status of a toe load test (e.g., from PENDING to OK/NOT_OK).
     */
    FinalToeLoadTestResponse updateStatus(Long id, String status, String userId);

    /**
     * Update remarks for a toe load test.
     */
    FinalToeLoadTestResponse updateRemarks(Long id, String remarks, String userId);

    /**
     * Delete a toe load test and all its samples.
     */
    void deleteToeLoadTest(Long id);
}

