package com.sarthi.service.Impl;

import com.sarthi.dto.*;
import com.sarthi.entity.*;
import com.sarthi.repository.*;
import com.sarthi.service.RmInspectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RmInspectionService.
 * Handles saving and retrieving Raw Material inspection data.
 */
@Service
public class RmInspectionServiceImpl implements RmInspectionService {

    private static final Logger logger = LoggerFactory.getLogger(RmInspectionServiceImpl.class);

    @Autowired
    private RmInspectionSummaryRepository summaryRepository;

    @Autowired
    private RmHeatFinalResultRepository heatResultRepository;

    @Autowired
    private RmVisualInspectionRepository visualRepository;

    @Autowired
    private RmDimensionalCheckRepository dimensionalRepository;

    @Autowired
    private RmMaterialTestingRepository materialTestingRepository;

    @Autowired
    private RmPackingStorageRepository packingRepository;

    @Autowired
    private RmCalibrationDocumentsRepository calibrationRepository;

    @Override
    @Transactional
    public String finishInspection(RmFinishInspectionDto dto) {
        String callNo = dto.getInspectionCallNo();
        logger.info("Finishing RM inspection for call: {}", callNo);

        // Validate all data before saving
        List<String> validationErrors = validateInspectionData(dto);
        if (!validationErrors.isEmpty()) {
            String errorMsg = String.join("; ", validationErrors);
            logger.error("Validation failed for call {}: {}", callNo, errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        // 1. Save Summary
        saveSummary(dto);

        // 2. Save Heat Final Results
        if (dto.getHeatFinalResults() != null) {
            saveHeatFinalResults(callNo, dto.getHeatFinalResults());
        }

        // 3. Save Visual Inspection Data
        if (dto.getVisualInspectionData() != null) {
            saveVisualInspection(callNo, dto.getVisualInspectionData());
        }

        // 4. Save Dimensional Check Data
        if (dto.getDimensionalCheckData() != null) {
            saveDimensionalCheck(callNo, dto.getDimensionalCheckData());
        }

        // 5. Save Material Testing Data
        if (dto.getMaterialTestingData() != null) {
            saveMaterialTesting(callNo, dto.getMaterialTestingData());
        }

        // 6. Save Packing & Storage Data
        if (dto.getPackingStorageData() != null) {
            savePackingStorage(callNo, dto.getPackingStorageData());
        }

        // 7. Save Calibration Documents Data
        if (dto.getCalibrationDocumentsData() != null) {
            saveCalibrationDocuments(callNo, dto.getCalibrationDocumentsData());
        }

        logger.info("RM inspection saved successfully for call: {}", callNo);
        return "Raw Material Inspection saved successfully";
    }

    private void saveSummary(RmFinishInspectionDto dto) {
        RmPreInspectionDataDto pre = dto.getPreInspectionData();
        RmInspectorDetailsDto inspector = dto.getInspectorDetails();
        String callNo = dto.getInspectionCallNo();

        RmInspectionSummary summary = summaryRepository.findByInspectionCallNo(callNo)
            .orElse(new RmInspectionSummary());

        summary.setInspectionCallNo(callNo);

        if (pre != null) {
            summary.setTotalHeatsOffered(pre.getTotalHeatsOffered());
            summary.setTotalQtyOfferedMt(pre.getTotalQtyOfferedMt());
            summary.setNumberOfBundles(pre.getNumberOfBundles());
            summary.setNumberOfErc(pre.getNumberOfErc());
            summary.setProductModel(pre.getProductModel());
            summary.setPoNo(pre.getPoNo());
            summary.setPoDate(parseDate(pre.getPoDate()));
            summary.setVendorName(pre.getVendorName());
            summary.setPlaceOfInspection(pre.getPlaceOfInspection());
            summary.setSourceOfRawMaterial(pre.getSourceOfRawMaterial());
        }

        if (inspector != null) {
            summary.setFinishedBy(inspector.getFinishedBy());
            summary.setFinishedAt(parseDateTime(inspector.getFinishedAt()));
            summary.setInspectionDate(parseDate(inspector.getInspectionDate()));
            summary.setShiftOfInspection(inspector.getShiftOfInspection());
        }

        summaryRepository.save(summary);
    }

    private void saveHeatFinalResults(String callNo, List<RmHeatFinalResultDto> results) {
        heatResultRepository.deleteByInspectionCallNo(callNo);
        for (RmHeatFinalResultDto dto : results) {
            RmHeatFinalResult entity = new RmHeatFinalResult();
            entity.setInspectionCallNo(callNo);
            entity.setHeatIndex(dto.getHeatIndex());
            entity.setHeatNo(dto.getHeatNo());
            entity.setTcNo(dto.getTcNo());
            entity.setTcDate(parseDate(dto.getTcDate()));
            entity.setManufacturerName(dto.getManufacturerName());
            entity.setInvoiceNumber(dto.getInvoiceNumber());
            entity.setInvoiceDate(parseDate(dto.getInvoiceDate()));
            entity.setSubPoNumber(dto.getSubPoNumber());
            entity.setSubPoDate(parseDate(dto.getSubPoDate()));
            entity.setSubPoQty(dto.getSubPoQty());
            entity.setTotalValueOfPo(dto.getTotalValueOfPo());
            entity.setTcQuantity(dto.getTcQuantity());
            entity.setOfferedQty(dto.getOfferedQty());
            entity.setColorCode(dto.getColorCode());
            entity.setStatus(dto.getStatus());
            entity.setWeightOfferedMt(dto.getWeightOfferedMt());
            entity.setWeightAcceptedMt(dto.getWeightAcceptedMt());
            entity.setWeightRejectedMt(dto.getWeightRejectedMt());
            entity.setCalibrationStatus(dto.getCalibrationStatus());
            entity.setVisualStatus(dto.getVisualStatus());
            entity.setDimensionalStatus(dto.getDimensionalStatus());
            entity.setMaterialTestStatus(dto.getMaterialTestStatus());
            entity.setPackingStatus(dto.getPackingStatus());
            entity.setRemarks(dto.getRemarks());
            heatResultRepository.save(entity);
        }
    }

    private void saveVisualInspection(String callNo, List<RmVisualInspectionDto> data) {
        visualRepository.deleteByInspectionCallNo(callNo);
        for (RmVisualInspectionDto dto : data) {
            RmVisualInspection entity = new RmVisualInspection();
            entity.setInspectionCallNo(callNo);
            entity.setHeatNo(dto.getHeatNo());
            entity.setHeatIndex(dto.getHeatIndex());
            entity.setDefectName(dto.getDefectName());
            entity.setIsSelected(dto.getIsSelected());
            entity.setDefectLengthMm(dto.getDefectLengthMm());
            visualRepository.save(entity);
        }
    }

    private void saveDimensionalCheck(String callNo, List<RmDimensionalCheckDto> data) {
        dimensionalRepository.deleteByInspectionCallNo(callNo);
        for (RmDimensionalCheckDto dto : data) {
            RmDimensionalCheck entity = new RmDimensionalCheck();
            entity.setInspectionCallNo(callNo);
            entity.setHeatNo(dto.getHeatNo());
            entity.setHeatIndex(dto.getHeatIndex());
            entity.setSampleNumber(dto.getSampleNumber());
            entity.setDiameter(dto.getDiameter());
            dimensionalRepository.save(entity);
        }
    }

    private void saveMaterialTesting(String callNo, List<RmMaterialTestingDto> data) {
        materialTestingRepository.deleteByInspectionCallNo(callNo);
        for (RmMaterialTestingDto dto : data) {
            RmMaterialTesting entity = new RmMaterialTesting();
            entity.setInspectionCallNo(callNo);
            entity.setHeatNo(dto.getHeatNo());
            entity.setHeatIndex(dto.getHeatIndex());
            entity.setSampleNumber(dto.getSampleNumber());
            entity.setCarbonPercent(dto.getCarbonPercent());
            entity.setSiliconPercent(dto.getSiliconPercent());
            entity.setManganesePercent(dto.getManganesePercent());
            entity.setPhosphorusPercent(dto.getPhosphorusPercent());
            entity.setSulphurPercent(dto.getSulphurPercent());
            entity.setGrainSize(dto.getGrainSize());
            // Map frontend field names to entity field names
            entity.setHardness(dto.getHardnessHrc());
            entity.setDecarb(dto.getDecarbDepthMm());
            entity.setInclusionA(dto.getInclusionA());
            entity.setInclusionB(dto.getInclusionB());
            entity.setInclusionC(dto.getInclusionC());
            entity.setInclusionD(dto.getInclusionD());
            entity.setRemarks(dto.getRemarks());
            materialTestingRepository.save(entity);
        }
    }

    private void savePackingStorage(String callNo, RmPackingStorageDto dto) {
        packingRepository.deleteByInspectionCallNo(callNo);
        RmPackingStorage entity = new RmPackingStorage();
        entity.setInspectionCallNo(callNo);
        entity.setBundlingSecure(dto.getBundlingSecure());
        entity.setTagsAttached(dto.getTagsAttached());
        entity.setLabelsCorrect(dto.getLabelsCorrect());
        entity.setProtectionAdequate(dto.getProtectionAdequate());
        entity.setStorageCondition(dto.getStorageCondition());
        entity.setMoistureProtection(dto.getMoistureProtection());
        entity.setStackingProper(dto.getStackingProper());
        entity.setRemarks(dto.getRemarks());
        packingRepository.save(entity);
    }

    private void saveCalibrationDocuments(String callNo, List<RmCalibrationDocumentsDto> data) {
        calibrationRepository.deleteByInspectionCallNo(callNo);
        for (RmCalibrationDocumentsDto dto : data) {
            RmCalibrationDocuments entity = new RmCalibrationDocuments();
            entity.setInspectionCallNo(callNo);
            entity.setHeatNo(dto.getHeatNo());
            entity.setHeatIndex(dto.getHeatIndex());
            entity.setRdsoApprovalId(dto.getRdsoApprovalId());
            entity.setRdsoValidFrom(parseDate(dto.getRdsoValidFrom()));
            entity.setRdsoValidTo(parseDate(dto.getRdsoValidTo()));
            entity.setGaugesAvailable(dto.getGaugesAvailable());
            entity.setLadleCarbonPercent(dto.getLadleCarbonPercent());
            entity.setLadleSiliconPercent(dto.getLadleSiliconPercent());
            entity.setLadleManganesePercent(dto.getLadleManganesePercent());
            entity.setLadlePhosphorusPercent(dto.getLadlePhosphorusPercent());
            entity.setLadleSulphurPercent(dto.getLadleSulphurPercent());
            entity.setVendorVerified(dto.getVendorVerified());
            entity.setVerifiedBy(dto.getVerifiedBy());
            entity.setVerifiedAt(parseDateTime(dto.getVerifiedAt()));
            calibrationRepository.save(entity);
        }
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr.substring(0, 10));
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) return null;
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public RmFinishInspectionDto getByCallNo(String callNo) {
        // To be implemented for fetching data
        return null;
    }

    /**
     * Validates inspection data and returns list of user-friendly error messages.
     */
    private List<String> validateInspectionData(RmFinishInspectionDto dto) {
        List<String> errors = new ArrayList<>();

        // Validate Material Testing Data
        if (dto.getMaterialTestingData() != null) {
            for (RmMaterialTestingDto mt : dto.getMaterialTestingData()) {
                String heatNo = mt.getHeatNo();
                int sample = mt.getSampleNumber() != null ? mt.getSampleNumber() : 0;
                String prefix = "Heat " + heatNo + " Sample " + sample + ": ";

                // Chemical composition validations (max value 99.9999 for DECIMAL(6,4))
                if (mt.getCarbonPercent() != null && mt.getCarbonPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Carbon % value (" + mt.getCarbonPercent() + ") is too large. Max allowed: 99%");
                }
                if (mt.getSiliconPercent() != null && mt.getSiliconPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Silicon % value (" + mt.getSiliconPercent() + ") is too large. Max allowed: 99%");
                }
                if (mt.getManganesePercent() != null && mt.getManganesePercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Manganese % value (" + mt.getManganesePercent() + ") is too large. Max allowed: 99%");
                }
                if (mt.getPhosphorusPercent() != null && mt.getPhosphorusPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Phosphorus % value (" + mt.getPhosphorusPercent() + ") is too large. Max allowed: 99%");
                }
                if (mt.getSulphurPercent() != null && mt.getSulphurPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Sulphur % value (" + mt.getSulphurPercent() + ") is too large. Max allowed: 99%");
                }

                // Inclusion ratings validation (max value 99.99 for DECIMAL(4,2))
                if (mt.getInclusionA() != null && mt.getInclusionA().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Inclusion A value (" + mt.getInclusionA() + ") is too large. Max allowed: 99");
                }
                if (mt.getInclusionB() != null && mt.getInclusionB().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Inclusion B value (" + mt.getInclusionB() + ") is too large. Max allowed: 99");
                }
                if (mt.getInclusionC() != null && mt.getInclusionC().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Inclusion C value (" + mt.getInclusionC() + ") is too large. Max allowed: 99");
                }
                if (mt.getInclusionD() != null && mt.getInclusionD().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Inclusion D value (" + mt.getInclusionD() + ") is too large. Max allowed: 99");
                }
            }
        }

        // Validate Calibration Documents Data
        if (dto.getCalibrationDocumentsData() != null) {
            for (RmCalibrationDocumentsDto cal : dto.getCalibrationDocumentsData()) {
                String heatNo = cal.getHeatNo();
                String prefix = "Heat " + heatNo + " Calibration: ";

                if (cal.getLadleCarbonPercent() != null && cal.getLadleCarbonPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Ladle Carbon % value is too large. Max allowed: 99%");
                }
                if (cal.getLadleSiliconPercent() != null && cal.getLadleSiliconPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Ladle Silicon % value is too large. Max allowed: 99%");
                }
                if (cal.getLadleManganesePercent() != null && cal.getLadleManganesePercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Ladle Manganese % value is too large. Max allowed: 99%");
                }
                if (cal.getLadlePhosphorusPercent() != null && cal.getLadlePhosphorusPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Ladle Phosphorus % value is too large. Max allowed: 99%");
                }
                if (cal.getLadleSulphurPercent() != null && cal.getLadleSulphurPercent().compareTo(new BigDecimal("99")) > 0) {
                    errors.add(prefix + "Ladle Sulphur % value is too large. Max allowed: 99%");
                }
            }
        }

        return errors;
    }
}

