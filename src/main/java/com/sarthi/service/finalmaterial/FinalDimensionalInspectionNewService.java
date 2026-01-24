package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalDimensionalInspectionRequest;
import com.sarthi.dto.finalmaterial.FinalDimensionalInspectionResponse;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Dimensional Inspection (New Parent-Child Design)
 * 
 * Handles business logic for dimensional inspection data with parent-child structure.
 * Supports pausing and resuming inspections.
 */
public interface FinalDimensionalInspectionNewService {

    /**
     * Save or update dimensional inspection with samples.
     * Supports both first save (create) and subsequent saves (pause/resume).
     */
    FinalDimensionalInspectionResponse saveOrUpdateDimensionalInspection(
            FinalDimensionalInspectionRequest request, String userId);

    /**
     * Get dimensional inspection by unique combination (call, lot, heat).
     */
    Optional<FinalDimensionalInspectionResponse> getDimensionalInspection(
            String inspectionCallNo, String lotNo, String heatNo);

    /**
     * Get all dimensional inspections for a call number.
     */
    List<FinalDimensionalInspectionResponse> getDimensionalInspectionsByCall(String inspectionCallNo);

    /**
     * Get dimensional inspection by ID.
     */
    Optional<FinalDimensionalInspectionResponse> getDimensionalInspectionById(Long id);

    /**
     * Update status of dimensional inspection.
     */
    FinalDimensionalInspectionResponse updateStatus(Long id, String status, String userId);

    /**
     * Update remarks of dimensional inspection.
     */
    FinalDimensionalInspectionResponse updateRemarks(Long id, String remarks, String userId);

    /**
     * Delete dimensional inspection (cascades to samples).
     */
    void deleteDimensionalInspection(Long id);

    /**
     * Delete all dimensional inspections for a call.
     */
    void deleteDimensionalInspectionsByCall(String inspectionCallNo);
}

