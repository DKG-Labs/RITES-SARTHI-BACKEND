package com.sarthi.service.finalmaterial;

import com.sarthi.dto.finalmaterial.FinalDimensionalInspectionFlatDto;
import com.sarthi.entity.finalmaterial.FinalDimensionalInspectionFlat;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Dimensional Inspection - FLAT STRUCTURE
 * 
 * Handles business logic for dimensional inspection data with flat fields.
 * This is the OLD structure used by Final Visual Inspection page.
 */
public interface FinalDimensionalInspectionFlatService {

    /**
     * Save a new dimensional inspection record
     */
    FinalDimensionalInspectionFlat saveDimensionalInspection(FinalDimensionalInspectionFlatDto dto, String userId);

    /**
     * Get dimensional inspection by ID
     */
    Optional<FinalDimensionalInspectionFlat> getDimensionalInspectionById(Long id);

    /**
     * Get all dimensional inspection records by inspection call number
     */
    List<FinalDimensionalInspectionFlat> getDimensionalInspectionByCallNo(String inspectionCallNo);

    /**
     * Get dimensional inspection by call number and lot number
     */
    Optional<FinalDimensionalInspectionFlat> getDimensionalInspectionByCallNoAndLotNo(
            String inspectionCallNo, String lotNo);

    /**
     * Get all dimensional inspection records by heat number
     */
    List<FinalDimensionalInspectionFlat> getDimensionalInspectionByCallNoAndHeatNo(
            String inspectionCallNo, String heatNo);

    /**
     * Update an existing dimensional inspection record
     */
    FinalDimensionalInspectionFlat updateDimensionalInspection(FinalDimensionalInspectionFlatDto dto, String userId);

    /**
     * Delete dimensional inspection record
     */
    void deleteDimensionalInspection(Long id);

    /**
     * Delete all dimensional inspection records for a call
     */
    void deleteDimensionalInspectionByCallNo(String inspectionCallNo);
}

