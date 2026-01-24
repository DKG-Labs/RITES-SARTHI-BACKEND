package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalApplicationDeflectionRequest;
import com.sarthi.dto.finalmaterial.FinalApplicationDeflectionResponse;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Application & Deflection Test (New Parent-Child Design)
 * 
 * Handles business logic for application & deflection test data with parent-child structure.
 * Supports pausing and resuming inspections.
 */
public interface FinalApplicationDeflectionNewService {

    /**
     * Save or update application & deflection test with samples.
     * Supports both first save (create) and subsequent saves (pause/resume).
     */
    FinalApplicationDeflectionResponse saveOrUpdateApplicationDeflection(
            FinalApplicationDeflectionRequest request, String userId);

    /**
     * Get application & deflection test by unique combination (call, lot, heat).
     */
    Optional<FinalApplicationDeflectionResponse> getApplicationDeflection(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Get all application & deflection tests for a call number.
     */
    List<FinalApplicationDeflectionResponse> getApplicationDeflectionsByCall(String inspectionCallNo);

    /**
     * Get application & deflection test by ID.
     */
    Optional<FinalApplicationDeflectionResponse> getApplicationDeflectionById(Long id);

    /**
     * Update status of application & deflection test.
     */
    FinalApplicationDeflectionResponse updateStatus(Long id, String status, String userId);

    /**
     * Update remarks of application & deflection test.
     */
    FinalApplicationDeflectionResponse updateRemarks(Long id, String remarks, String userId);

    /**
     * Delete application & deflection test (cascades to samples).
     */
    void deleteApplicationDeflection(Long id);

    /**
     * Delete all application & deflection tests for a call.
     */
    void deleteApplicationDeflectionsByCall(String inspectionCallNo);
}

