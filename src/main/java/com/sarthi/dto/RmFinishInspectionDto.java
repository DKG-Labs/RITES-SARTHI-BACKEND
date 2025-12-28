package com.sarthi.dto;

import lombok.Data;
import java.util.List;

/**
 * Main DTO for receiving Raw Material inspection finish payload.
 * Contains all submodule data collected by the inspector.
 */
@Data
public class RmFinishInspectionDto {

    private String inspectionCallNo;
    private RmPreInspectionDataDto preInspectionData;
    private List<RmHeatFinalResultDto> heatFinalResults;
    private List<RmVisualInspectionDto> visualInspectionData;
    private List<RmDimensionalCheckDto> dimensionalCheckData;
    private List<RmMaterialTestingDto> materialTestingData;
    private List<RmPackingStorageDto> packingStorageData;  // Changed to List - per heat
    private List<RmCalibrationDocumentsDto> calibrationDocumentsData;
    private RmInspectorDetailsDto inspectorDetails;
}

