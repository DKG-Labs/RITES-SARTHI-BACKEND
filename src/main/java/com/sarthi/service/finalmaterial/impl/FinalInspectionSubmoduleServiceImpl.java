package com.sarthi.service.finalmaterial.impl;

import com.sarthi.entity.finalmaterial.*;
import com.sarthi.repository.finalmaterial.*;
import com.sarthi.service.finalmaterial.FinalInspectionSubmoduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Final Inspection Submodule Service
 * Handles CRUD operations for all final inspection submodule data with audit tracking
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FinalInspectionSubmoduleServiceImpl implements FinalInspectionSubmoduleService {

    private final FinalCalibrationDocumentsRepository calibrationDocumentsRepository;
    private final FinalVisualDimensionalRepository visualDimensionalRepository;
    private final FinalChemicalAnalysisRepository chemicalAnalysisRepository;
    private final FinalHardnessTestRepository hardnessTestRepository;
    private final FinalInclusionRatingRepository inclusionRatingRepository;
    private final FinalApplicationDeflectionRepository applicationDeflectionRepository;
    private final FinalWeightTestRepository weightTestRepository;
    private final FinalToeLoadTestRepository toeLoadTestRepository;

    // ===== CALIBRATION & DOCUMENTS =====
    @Override
    public FinalCalibrationDocuments saveCalibrationDocuments(FinalCalibrationDocuments data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return calibrationDocumentsRepository.save(data);
    }

    @Override
    public List<FinalCalibrationDocuments> getCalibrationDocumentsByCallNo(String inspectionCallNo) {
        return calibrationDocumentsRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalCalibrationDocuments> getCalibrationDocumentsByLot(String inspectionCallNo, String lotNo) {
        return calibrationDocumentsRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalCalibrationDocuments> getCalibrationDocumentsById(Long id) {
        return calibrationDocumentsRepository.findById(id);
    }

    @Override
    public FinalCalibrationDocuments updateCalibrationDocuments(FinalCalibrationDocuments data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return calibrationDocumentsRepository.save(data);
    }

    @Override
    public void deleteCalibrationDocuments(Long id) {
        calibrationDocumentsRepository.deleteById(id);
    }

    // ===== VISUAL & DIMENSIONAL =====
    @Override
    public FinalVisualDimensional saveVisualDimensional(FinalVisualDimensional data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return visualDimensionalRepository.save(data);
    }

    @Override
    public List<FinalVisualDimensional> getVisualDimensionalByCallNo(String inspectionCallNo) {
        return visualDimensionalRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalVisualDimensional> getVisualDimensionalByLot(String inspectionCallNo, String lotNo) {
        return visualDimensionalRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalVisualDimensional> getVisualDimensionalById(Long id) {
        return visualDimensionalRepository.findById(id);
    }

    @Override
    public FinalVisualDimensional updateVisualDimensional(FinalVisualDimensional data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return visualDimensionalRepository.save(data);
    }

    @Override
    public void deleteVisualDimensional(Long id) {
        visualDimensionalRepository.deleteById(id);
    }

    // ===== CHEMICAL ANALYSIS =====
    @Override
    public FinalChemicalAnalysis saveChemicalAnalysis(FinalChemicalAnalysis data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return chemicalAnalysisRepository.save(data);
    }

    @Override
    public List<FinalChemicalAnalysis> getChemicalAnalysisByCallNo(String inspectionCallNo) {
        return chemicalAnalysisRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalChemicalAnalysis> getChemicalAnalysisByLot(String inspectionCallNo, String lotNo) {
        return chemicalAnalysisRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalChemicalAnalysis> getChemicalAnalysisById(Long id) {
        return chemicalAnalysisRepository.findById(id);
    }

    @Override
    public FinalChemicalAnalysis updateChemicalAnalysis(FinalChemicalAnalysis data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return chemicalAnalysisRepository.save(data);
    }

    @Override
    public void deleteChemicalAnalysis(Long id) {
        chemicalAnalysisRepository.deleteById(id);
    }

    // ===== HARDNESS TEST =====
    @Override
    public FinalHardnessTest saveHardnessTest(FinalHardnessTest data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return hardnessTestRepository.save(data);
    }

    @Override
    public List<FinalHardnessTest> getHardnessTestByCallNo(String inspectionCallNo) {
        return hardnessTestRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalHardnessTest> getHardnessTestByLot(String inspectionCallNo, String lotNo) {
        return hardnessTestRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalHardnessTest> getHardnessTestById(Long id) {
        return hardnessTestRepository.findById(id);
    }

    @Override
    public FinalHardnessTest updateHardnessTest(FinalHardnessTest data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return hardnessTestRepository.save(data);
    }

    @Override
    public void deleteHardnessTest(Long id) {
        hardnessTestRepository.deleteById(id);
    }

    // ===== INCLUSION & DECARB =====
    @Override
    public FinalInclusionRating saveInclusionRating(FinalInclusionRating data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return inclusionRatingRepository.save(data);
    }

    @Override
    public List<FinalInclusionRating> getInclusionRatingByCallNo(String inspectionCallNo) {
        return inclusionRatingRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalInclusionRating> getInclusionRatingByLot(String inspectionCallNo, String lotNo) {
        return inclusionRatingRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalInclusionRating> getInclusionRatingById(Long id) {
        return inclusionRatingRepository.findById(id);
    }

    @Override
    public FinalInclusionRating updateInclusionRating(FinalInclusionRating data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return inclusionRatingRepository.save(data);
    }

    @Override
    public void deleteInclusionRating(Long id) {
        inclusionRatingRepository.deleteById(id);
    }

    // ===== DEFLECTION TEST =====
    @Override
    public FinalApplicationDeflection saveApplicationDeflection(FinalApplicationDeflection data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return applicationDeflectionRepository.save(data);
    }

    @Override
    public List<FinalApplicationDeflection> getApplicationDeflectionByCallNo(String inspectionCallNo) {
        return applicationDeflectionRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalApplicationDeflection> getApplicationDeflectionByLot(String inspectionCallNo, String lotNo) {
        return applicationDeflectionRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalApplicationDeflection> getApplicationDeflectionById(Long id) {
        return applicationDeflectionRepository.findById(id);
    }

    @Override
    public FinalApplicationDeflection updateApplicationDeflection(FinalApplicationDeflection data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return applicationDeflectionRepository.save(data);
    }

    @Override
    public void deleteApplicationDeflection(Long id) {
        applicationDeflectionRepository.deleteById(id);
    }

    // ===== WEIGHT TEST =====
    @Override
    public FinalWeightTest saveWeightTest(FinalWeightTest data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return weightTestRepository.save(data);
    }

    @Override
    public List<FinalWeightTest> getWeightTestByCallNo(String inspectionCallNo) {
        return weightTestRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalWeightTest> getWeightTestByLot(String inspectionCallNo, String lotNo) {
        return weightTestRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalWeightTest> getWeightTestById(Long id) {
        return weightTestRepository.findById(id);
    }

    @Override
    public FinalWeightTest updateWeightTest(FinalWeightTest data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return weightTestRepository.save(data);
    }

    @Override
    public void deleteWeightTest(Long id) {
        weightTestRepository.deleteById(id);
    }

    // ===== TOE LOAD TEST =====
    @Override
    public FinalToeLoadTest saveToeLoadTest(FinalToeLoadTest data, String userId) {
        data.setCreatedBy(userId);
        data.setUpdatedBy(userId);
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return toeLoadTestRepository.save(data);
    }

    @Override
    public List<FinalToeLoadTest> getToeLoadTestByCallNo(String inspectionCallNo) {
        return toeLoadTestRepository.findByInspectionCallNo(inspectionCallNo);
    }

    @Override
    public List<FinalToeLoadTest> getToeLoadTestByLot(String inspectionCallNo, String lotNo) {
        return toeLoadTestRepository.findByInspectionCallNoAndLotNo(inspectionCallNo, lotNo);
    }

    @Override
    public Optional<FinalToeLoadTest> getToeLoadTestById(Long id) {
        return toeLoadTestRepository.findById(id);
    }

    @Override
    public FinalToeLoadTest updateToeLoadTest(FinalToeLoadTest data, String userId) {
        data.setUpdatedBy(userId);
        data.setUpdatedAt(LocalDateTime.now());
        return toeLoadTestRepository.save(data);
    }

    @Override
    public void deleteToeLoadTest(Long id) {
        toeLoadTestRepository.deleteById(id);
    }
}

