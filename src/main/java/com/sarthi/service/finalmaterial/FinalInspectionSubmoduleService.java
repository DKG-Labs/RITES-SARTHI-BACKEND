package com.sarthi.service.finalmaterial;

import com.sarthi.entity.finalmaterial.*;
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

    // ===== VISUAL & DIMENSIONAL =====
    FinalVisualDimensional saveVisualDimensional(FinalVisualDimensional data, String userId);
    List<FinalVisualDimensional> getVisualDimensionalByCallNo(String inspectionCallNo);
    List<FinalVisualDimensional> getVisualDimensionalByLot(String inspectionCallNo, String lotNo);
    Optional<FinalVisualDimensional> getVisualDimensionalById(Long id);
    FinalVisualDimensional updateVisualDimensional(FinalVisualDimensional data, String userId);
    void deleteVisualDimensional(Long id);

    // ===== CHEMICAL ANALYSIS =====
    FinalChemicalAnalysis saveChemicalAnalysis(FinalChemicalAnalysis data, String userId);
    List<FinalChemicalAnalysis> getChemicalAnalysisByCallNo(String inspectionCallNo);
    List<FinalChemicalAnalysis> getChemicalAnalysisByLot(String inspectionCallNo, String lotNo);
    Optional<FinalChemicalAnalysis> getChemicalAnalysisById(Long id);
    FinalChemicalAnalysis updateChemicalAnalysis(FinalChemicalAnalysis data, String userId);
    void deleteChemicalAnalysis(Long id);

    // ===== HARDNESS TEST =====
    FinalHardnessTest saveHardnessTest(FinalHardnessTest data, String userId);
    List<FinalHardnessTest> getHardnessTestByCallNo(String inspectionCallNo);
    List<FinalHardnessTest> getHardnessTestByLot(String inspectionCallNo, String lotNo);
    Optional<FinalHardnessTest> getHardnessTestById(Long id);
    FinalHardnessTest updateHardnessTest(FinalHardnessTest data, String userId);
    void deleteHardnessTest(Long id);

    // ===== INCLUSION & DECARB =====
    FinalInclusionRating saveInclusionRating(FinalInclusionRating data, String userId);
    List<FinalInclusionRating> getInclusionRatingByCallNo(String inspectionCallNo);
    List<FinalInclusionRating> getInclusionRatingByLot(String inspectionCallNo, String lotNo);
    Optional<FinalInclusionRating> getInclusionRatingById(Long id);
    FinalInclusionRating updateInclusionRating(FinalInclusionRating data, String userId);
    void deleteInclusionRating(Long id);

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
}

