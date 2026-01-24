package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalVisualInspectionDto;
import com.sarthi.entity.finalmaterial.FinalVisualInspection;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Visual Inspection
 * Handles business logic for visual inspection data
 */
public interface FinalVisualInspectionService {

    /**
     * Save a new visual inspection record
     */
    FinalVisualInspection saveVisualInspection(FinalVisualInspectionDto dto, String userId);

    /**
     * Get visual inspection by ID
     */
    Optional<FinalVisualInspection> getVisualInspectionById(Long id);

    /**
     * Get all visual inspection records by inspection call number
     */
    List<FinalVisualInspection> getVisualInspectionByCallNo(String inspectionCallNo);

    /**
     * Get visual inspection by call number and lot number
     */
    Optional<FinalVisualInspection> getVisualInspectionByCallNoAndLotNo(String inspectionCallNo, String lotNo);

    /**
     * Get all visual inspection records by heat number
     */
    List<FinalVisualInspection> getVisualInspectionByCallNoAndHeatNo(String inspectionCallNo, String heatNo);

    /**
     * Update an existing visual inspection record
     */
    FinalVisualInspection updateVisualInspection(FinalVisualInspectionDto dto, String userId);

    /**
     * Delete visual inspection record
     */
    void deleteVisualInspection(Long id);

    /**
     * Delete all visual inspection records for a call
     */
    void deleteVisualInspectionByCallNo(String inspectionCallNo);
}

