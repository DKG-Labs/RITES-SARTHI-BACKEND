package com.sarthi.service.finalmaterial;

import com.sarthi.entity.finalmaterial.*;
import com.sarthi.dto.finalmaterial.FinalLadleValuesDto;
import com.sarthi.dto.finalmaterial.FinalChemicalAnalysisRequest;
import com.sarthi.dto.finalmaterial.FinalChemicalAnalysisResponse;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Final Inspection Submodules
 * Handles CRUD operations for all final inspection submodule data
 */
public interface FinalInspectionSubmoduleService {

    // ===== CALIBRATION & DOCUMENTS =====
    FinalCalibrationDocuments saveCalibrationDocuments(FinalCalibrationDocuments data, String userId);
    List<FinalCalibrationDocuments> getCalibrationDocumentsByCallNo(String inspectionCallNo);
    List<FinalCalibrationDocuments> getCalibrationDocumentsByLot(String inspectionCallNo, String lotNo);
    Optional<FinalCalibrationDocuments> getCalibrationDocumentsById(Long id);
    FinalCalibrationDocuments updateCalibrationDocuments(FinalCalibrationDocuments data, String userId);
    void deleteCalibrationDocuments(Long id);

    // ===== VISUAL INSPECTION =====
    // Note: Visual inspection is now handled by FinalVisualInspectionService
    // Use FinalVisualInspectionService for visual inspection operations

    // ===== DIMENSIONAL INSPECTION =====
    // Note: Dimensional inspection is now handled by FinalDimensionalInspectionService
    // Use FinalDimensionalInspectionService for dimensional inspection operations

    // ===== CHEMICAL ANALYSIS =====
    FinalChemicalAnalysisResponse saveChemicalAnalysis(FinalChemicalAnalysisRequest request, String userId);
    List<FinalChemicalAnalysis> getChemicalAnalysisByCallNo(String inspectionCallNo);
    List<FinalChemicalAnalysis> getChemicalAnalysisByLot(String inspectionCallNo, String lotNo);
    Optional<FinalChemicalAnalysis> getChemicalAnalysisById(Long id);
    FinalChemicalAnalysis updateChemicalAnalysis(FinalChemicalAnalysis data, String userId);
    void deleteChemicalAnalysis(Long id);

    // ===== INCLUSION RATING - DEPRECATED (Use FinalInclusionRatingNewService instead) =====
    // Old flat structure methods removed - migrate to new parent-child structure

    // ===== DEFLECTION TEST =====
    FinalApplicationDeflection saveApplicationDeflection(FinalApplicationDeflection data, String userId);
    List<FinalApplicationDeflection> getApplicationDeflectionByCallNo(String inspectionCallNo);
    List<FinalApplicationDeflection> getApplicationDeflectionByLot(String inspectionCallNo, String lotNo);
    Optional<FinalApplicationDeflection> getApplicationDeflectionById(Long id);
    FinalApplicationDeflection updateApplicationDeflection(FinalApplicationDeflection data, String userId);
    void deleteApplicationDeflection(Long id);

    // ===== WEIGHT TEST =====
    FinalWeightTest saveWeightTest(FinalWeightTest data, String userId);
    List<FinalWeightTest> getWeightTestByCallNo(String inspectionCallNo);
    List<FinalWeightTest> getWeightTestByLot(String inspectionCallNo, String lotNo);
    Optional<FinalWeightTest> getWeightTestById(Long id);
    FinalWeightTest updateWeightTest(FinalWeightTest data, String userId);
    void deleteWeightTest(Long id);

    // ===== TOE LOAD TEST =====
    FinalToeLoadTest saveToeLoadTest(FinalToeLoadTest data, String userId);
    List<FinalToeLoadTest> getToeLoadTestByCallNo(String inspectionCallNo);
    List<FinalToeLoadTest> getToeLoadTestByLot(String inspectionCallNo, String lotNo);
    Optional<FinalToeLoadTest> getToeLoadTestById(Long id);
    FinalToeLoadTest updateToeLoadTest(FinalToeLoadTest data, String userId);
    void deleteToeLoadTest(Long id);

    // ===== LADLE VALUES =====
    /**
     * Get ladle values (chemical analysis from vendor) for all lots.
     * Used in Chemical Analysis page to display ladle values.
     * @param inspectionCallNo The inspection call number
     * @return List of ladle values per lot
     */
    List<FinalLadleValuesDto> getLadleValuesByCallNo(String inspectionCallNo);
}

