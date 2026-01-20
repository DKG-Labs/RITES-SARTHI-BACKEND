package com.sarthi.service.finalmaterial.impl;

import com.sarthi.entity.finalmaterial.*;
import com.sarthi.repository.finalmaterial.*;
import com.sarthi.service.finalmaterial.FinalInspectionSubmoduleService;
import com.sarthi.dto.finalmaterial.FinalLadleValuesDto;
import com.sarthi.dto.finalmaterial.FinalInclusionRatingBatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<FinalInclusionRating> saveInclusionRatingBatch(FinalInclusionRatingBatchDTO batchData, String userId) {
        List<FinalInclusionRating> savedRecords = new ArrayList<>();
        int sampleCount = batchData.getMicrostructure1st() != null ? batchData.getMicrostructure1st().size() : 0;

        for (int i = 0; i < sampleCount; i++) {
            FinalInclusionRating record = new FinalInclusionRating();
            record.setInspectionCallNo(batchData.getInspectionCallNo());
            record.setLotNo(batchData.getLotNo());
            record.setHeatNo(batchData.getHeatNo());
            record.setSampleNo(i + 1);

            // Set microstructure values
            if (batchData.getMicrostructure1st() != null && i < batchData.getMicrostructure1st().size()) {
                record.setMicrostructure1st(batchData.getMicrostructure1st().get(i));
            }
            if (batchData.getMicrostructure2nd() != null && i < batchData.getMicrostructure2nd().size()) {
                record.setMicrostructure2nd(batchData.getMicrostructure2nd().get(i));
            }

            // Set decarb values
            if (batchData.getDecarb1st() != null && i < batchData.getDecarb1st().size()) {
                String val = batchData.getDecarb1st().get(i);
                if (val != null && !val.isEmpty()) {
                    record.setDecarb1st(new BigDecimal(val));
                }
            }
            if (batchData.getDecarb2nd() != null && i < batchData.getDecarb2nd().size()) {
                String val = batchData.getDecarb2nd().get(i);
                if (val != null && !val.isEmpty()) {
                    record.setDecarb2nd(new BigDecimal(val));
                }
            }

            // Set inclusion values
            if (batchData.getInclusion1st() != null && i < batchData.getInclusion1st().size()) {
                Map<String, String> inclusion = batchData.getInclusion1st().get(i);
                if (inclusion != null) {
                    record.setInclusionARating(inclusion.get("A"));
                    record.setInclusionBRating(inclusion.get("B"));
                    record.setInclusionCRating(inclusion.get("C"));
                    record.setInclusionDRating(inclusion.get("D"));
                    record.setInclusionType(inclusion.get("type"));
                }
            }

            // Set defects value
            if (batchData.getDefects1st() != null && i < batchData.getDefects1st().size()) {
                record.setFreedomFromDefects(batchData.getDefects1st().get(i));
            }

            // Set remarks (shared across all samples)
            record.setRemarks(batchData.getInclusionRemarks());

            // Set audit fields
            record.setCreatedBy(userId);
            record.setUpdatedBy(userId);
            record.setCreatedAt(LocalDateTime.now());
            record.setUpdatedAt(LocalDateTime.now());

            savedRecords.add(inclusionRatingRepository.save(record));
        }

        return savedRecords;
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

    // ===== LADLE VALUES =====
    @Override
    public List<FinalLadleValuesDto> getLadleValuesByCallNo(String inspectionCallNo) {
        List<FinalChemicalAnalysis> entities = chemicalAnalysisRepository.findByInspectionCallNo(inspectionCallNo);
        List<FinalLadleValuesDto> dtos = new ArrayList<>();

        for (FinalChemicalAnalysis entity : entities) {
            FinalLadleValuesDto dto = FinalLadleValuesDto.builder()
                    .lotNo(entity.getLotNo())
                    .heatNo(entity.getHeatNo())
                    .percentC(entity.getCarbonPercent())
                    .percentSi(entity.getSiliconPercent())
                    .percentMn(entity.getManganesePercent())
                    .percentP(entity.getPhosphorusPercent())
                    .percentS(entity.getSulphurPercent())
                    .build();
            dtos.add(dto);
        }

        return dtos;
    }
}

