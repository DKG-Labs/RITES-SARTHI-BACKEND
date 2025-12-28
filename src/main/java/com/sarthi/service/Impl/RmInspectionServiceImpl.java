package com.sarthi.service.Impl;

import com.sarthi.dto.*;
import com.sarthi.entity.*;
import com.sarthi.entity.rawmaterial.RmChemicalAnalysis;
import com.sarthi.repository.*;
import com.sarthi.repository.rawmaterial.RmChemicalAnalysisRepository;
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

    @Autowired
    private RmChemicalAnalysisRepository chemicalAnalysisRepository;

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

    private void savePackingStorage(String callNo, List<RmPackingStorageDto> dtoList) {
        packingRepository.deleteByInspectionCallNo(callNo);
        for (RmPackingStorageDto dto : dtoList) {
            RmPackingStorage entity = new RmPackingStorage();
            entity.setInspectionCallNo(callNo);
            entity.setHeatNo(dto.getHeatNo());
            entity.setHeatIndex(dto.getHeatIndex());
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
        logger.info("Fetching RM inspection data for call: {}", callNo);

        // Check if inspection data exists
        if (!summaryRepository.existsByInspectionCallNo(callNo)) {
            logger.warn("No inspection data found for call: {}", callNo);
            return null;
        }

        RmFinishInspectionDto dto = new RmFinishInspectionDto();
        dto.setInspectionCallNo(callNo);

        // 1. Fetch Summary (Pre-Inspection Data + Inspector Details)
        summaryRepository.findByInspectionCallNo(callNo).ifPresent(summary -> {
            // Pre-Inspection Data
            RmPreInspectionDataDto preData = new RmPreInspectionDataDto();
            preData.setInspectionCallNo(callNo);
            preData.setTotalHeatsOffered(summary.getTotalHeatsOffered());
            preData.setTotalQtyOfferedMt(summary.getTotalQtyOfferedMt());
            preData.setNumberOfBundles(summary.getNumberOfBundles());
            preData.setNumberOfErc(summary.getNumberOfErc());
            preData.setProductModel(summary.getProductModel());
            preData.setPoNo(summary.getPoNo());
            preData.setPoDate(formatDate(summary.getPoDate()));
            preData.setVendorName(summary.getVendorName());
            preData.setPlaceOfInspection(summary.getPlaceOfInspection());
            preData.setSourceOfRawMaterial(summary.getSourceOfRawMaterial());
            dto.setPreInspectionData(preData);

            // Inspector Details
            RmInspectorDetailsDto inspectorData = new RmInspectorDetailsDto();
            inspectorData.setFinishedBy(summary.getFinishedBy());
            inspectorData.setFinishedAt(formatDateTime(summary.getFinishedAt()));
            inspectorData.setInspectionDate(formatDate(summary.getInspectionDate()));
            inspectorData.setShiftOfInspection(summary.getShiftOfInspection());
            dto.setInspectorDetails(inspectorData);
        });

        // 2. Fetch Heat Final Results
        List<RmHeatFinalResult> heatResults = heatResultRepository.findByInspectionCallNo(callNo);
        List<RmHeatFinalResultDto> heatResultDtos = new ArrayList<>();
        for (RmHeatFinalResult entity : heatResults) {
            RmHeatFinalResultDto heatDto = new RmHeatFinalResultDto();
            heatDto.setInspectionCallNo(entity.getInspectionCallNo());
            heatDto.setHeatIndex(entity.getHeatIndex());
            heatDto.setHeatNo(entity.getHeatNo());
            heatDto.setTcNo(entity.getTcNo());
            heatDto.setTcDate(formatDate(entity.getTcDate()));
            heatDto.setManufacturerName(entity.getManufacturerName());
            heatDto.setInvoiceNumber(entity.getInvoiceNumber());
            heatDto.setInvoiceDate(formatDate(entity.getInvoiceDate()));
            heatDto.setSubPoNumber(entity.getSubPoNumber());
            heatDto.setSubPoDate(formatDate(entity.getSubPoDate()));
            heatDto.setSubPoQty(entity.getSubPoQty());
            heatDto.setTotalValueOfPo(entity.getTotalValueOfPo());
            heatDto.setTcQuantity(entity.getTcQuantity());
            heatDto.setOfferedQty(entity.getOfferedQty());
            heatDto.setColorCode(entity.getColorCode());
            heatDto.setStatus(entity.getStatus());
            heatDto.setWeightOfferedMt(entity.getWeightOfferedMt());
            heatDto.setWeightAcceptedMt(entity.getWeightAcceptedMt());
            heatDto.setWeightRejectedMt(entity.getWeightRejectedMt());
            heatDto.setCalibrationStatus(entity.getCalibrationStatus());
            heatDto.setVisualStatus(entity.getVisualStatus());
            heatDto.setDimensionalStatus(entity.getDimensionalStatus());
            heatDto.setMaterialTestStatus(entity.getMaterialTestStatus());
            heatDto.setPackingStatus(entity.getPackingStatus());
            heatDto.setRemarks(entity.getRemarks());
            heatResultDtos.add(heatDto);
        }
        dto.setHeatFinalResults(heatResultDtos);

        // 3. Fetch Visual Inspection Data
        List<RmVisualInspection> visualEntities = visualRepository.findByInspectionCallNo(callNo);
        List<RmVisualInspectionDto> visualDtos = new ArrayList<>();
        for (RmVisualInspection entity : visualEntities) {
            RmVisualInspectionDto visualDto = new RmVisualInspectionDto();
            visualDto.setInspectionCallNo(entity.getInspectionCallNo());
            visualDto.setHeatNo(entity.getHeatNo());
            visualDto.setHeatIndex(entity.getHeatIndex());
            visualDto.setDefectName(entity.getDefectName());
            visualDto.setIsSelected(entity.getIsSelected());
            visualDto.setDefectLengthMm(entity.getDefectLengthMm());
            visualDtos.add(visualDto);
        }
        dto.setVisualInspectionData(visualDtos);

        // 4. Fetch Dimensional Check Data
        List<RmDimensionalCheck> dimensionalEntities = dimensionalRepository.findByInspectionCallNo(callNo);
        List<RmDimensionalCheckDto> dimensionalDtos = new ArrayList<>();
        for (RmDimensionalCheck entity : dimensionalEntities) {
            RmDimensionalCheckDto dimDto = new RmDimensionalCheckDto();
            dimDto.setInspectionCallNo(entity.getInspectionCallNo());
            dimDto.setHeatNo(entity.getHeatNo());
            dimDto.setHeatIndex(entity.getHeatIndex());
            dimDto.setSampleNumber(entity.getSampleNumber());
            dimDto.setDiameter(entity.getDiameter());
            dimensionalDtos.add(dimDto);
        }
        dto.setDimensionalCheckData(dimensionalDtos);

        // 5. Fetch Material Testing Data
        List<RmMaterialTesting> materialEntities = materialTestingRepository.findByInspectionCallNo(callNo);
        List<RmMaterialTestingDto> materialDtos = new ArrayList<>();
        for (RmMaterialTesting entity : materialEntities) {
            RmMaterialTestingDto matDto = new RmMaterialTestingDto();
            matDto.setInspectionCallNo(entity.getInspectionCallNo());
            matDto.setHeatNo(entity.getHeatNo());
            matDto.setHeatIndex(entity.getHeatIndex());
            matDto.setSampleNumber(entity.getSampleNumber());
            matDto.setCarbonPercent(entity.getCarbonPercent());
            matDto.setSiliconPercent(entity.getSiliconPercent());
            matDto.setManganesePercent(entity.getManganesePercent());
            matDto.setPhosphorusPercent(entity.getPhosphorusPercent());
            matDto.setSulphurPercent(entity.getSulphurPercent());
            matDto.setGrainSize(entity.getGrainSize());
            // Map entity field names to DTO field names
            matDto.setHardnessHrc(entity.getHardness());
            matDto.setDecarbDepthMm(entity.getDecarb());
            matDto.setInclusionA(entity.getInclusionA());
            matDto.setInclusionB(entity.getInclusionB());
            matDto.setInclusionC(entity.getInclusionC());
            matDto.setInclusionD(entity.getInclusionD());
            matDto.setRemarks(entity.getRemarks());
            materialDtos.add(matDto);
        }
        dto.setMaterialTestingData(materialDtos);

        // 6. Fetch Packing & Storage Data - per heat
        List<RmPackingStorage> packingEntities = packingRepository.findByInspectionCallNo(callNo);
        List<RmPackingStorageDto> packingDtos = new ArrayList<>();
        for (RmPackingStorage entity : packingEntities) {
            RmPackingStorageDto packingDto = new RmPackingStorageDto();
            packingDto.setInspectionCallNo(entity.getInspectionCallNo());
            packingDto.setHeatNo(entity.getHeatNo());
            packingDto.setHeatIndex(entity.getHeatIndex());
            packingDto.setBundlingSecure(entity.getBundlingSecure());
            packingDto.setTagsAttached(entity.getTagsAttached());
            packingDto.setLabelsCorrect(entity.getLabelsCorrect());
            packingDto.setProtectionAdequate(entity.getProtectionAdequate());
            packingDto.setStorageCondition(entity.getStorageCondition());
            packingDto.setMoistureProtection(entity.getMoistureProtection());
            packingDto.setStackingProper(entity.getStackingProper());
            packingDto.setRemarks(entity.getRemarks());
            packingDtos.add(packingDto);
        }
        dto.setPackingStorageData(packingDtos);

        // 7. Fetch Calibration Documents Data
        List<RmCalibrationDocuments> calibrationEntities = calibrationRepository.findByInspectionCallNo(callNo);
        List<RmCalibrationDocumentsDto> calibrationDtos = new ArrayList<>();
        for (RmCalibrationDocuments entity : calibrationEntities) {
            RmCalibrationDocumentsDto calDto = new RmCalibrationDocumentsDto();
            calDto.setInspectionCallNo(entity.getInspectionCallNo());
            calDto.setHeatNo(entity.getHeatNo());
            calDto.setHeatIndex(entity.getHeatIndex());
            calDto.setRdsoApprovalId(entity.getRdsoApprovalId());
            calDto.setRdsoValidFrom(formatDate(entity.getRdsoValidFrom()));
            calDto.setRdsoValidTo(formatDate(entity.getRdsoValidTo()));
            calDto.setGaugesAvailable(entity.getGaugesAvailable());
            calDto.setLadleCarbonPercent(entity.getLadleCarbonPercent());
            calDto.setLadleSiliconPercent(entity.getLadleSiliconPercent());
            calDto.setLadleManganesePercent(entity.getLadleManganesePercent());
            calDto.setLadlePhosphorusPercent(entity.getLadlePhosphorusPercent());
            calDto.setLadleSulphurPercent(entity.getLadleSulphurPercent());
            calDto.setVendorVerified(entity.getVendorVerified());
            calDto.setVerifiedBy(entity.getVerifiedBy());
            calDto.setVerifiedAt(formatDateTime(entity.getVerifiedAt()));
            calibrationDtos.add(calDto);
        }
        dto.setCalibrationDocumentsData(calibrationDtos);

        logger.info("Successfully fetched RM inspection data for call: {}", callNo);
        return dto;
    }

    /**
     * Format LocalDate to String (dd/MM/yyyy)
     */
    private String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Format LocalDateTime to ISO String
     */
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public RmPreInspectionDataDto getSummaryByCallNo(String callNo) {
        logger.info("Fetching cumulative summary data for call: {}", callNo);

        return summaryRepository.findByInspectionCallNo(callNo)
                .map(summary -> {
                    RmPreInspectionDataDto dto = new RmPreInspectionDataDto();
                    dto.setInspectionCallNo(callNo);
                    dto.setTotalHeatsOffered(summary.getTotalHeatsOffered());
                    dto.setTotalQtyOfferedMt(summary.getTotalQtyOfferedMt());
                    dto.setNumberOfBundles(summary.getNumberOfBundles());
                    dto.setNumberOfErc(summary.getNumberOfErc());
                    dto.setProductModel(summary.getProductModel());
                    dto.setPoNo(summary.getPoNo());
                    dto.setPoDate(formatDate(summary.getPoDate()));
                    dto.setVendorName(summary.getVendorName());
                    dto.setPlaceOfInspection(summary.getPlaceOfInspection());
                    dto.setSourceOfRawMaterial(summary.getSourceOfRawMaterial());
                    logger.info("Successfully fetched cumulative summary for call: {}", callNo);
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public List<RmHeatFinalResultDto> getFinalResultsByCallNo(String callNo) {
        logger.info("Fetching final inspection results for call: {}", callNo);

        List<RmHeatFinalResult> entities = heatResultRepository.findByInspectionCallNo(callNo);
        List<RmHeatFinalResultDto> dtos = new ArrayList<>();

        for (RmHeatFinalResult entity : entities) {
            RmHeatFinalResultDto dto = new RmHeatFinalResultDto();
            dto.setInspectionCallNo(entity.getInspectionCallNo());
            dto.setHeatIndex(entity.getHeatIndex());
            dto.setHeatNo(entity.getHeatNo());
            dto.setTcNo(entity.getTcNo());
            dto.setTcDate(formatDate(entity.getTcDate()));
            dto.setManufacturerName(entity.getManufacturerName());
            dto.setInvoiceNumber(entity.getInvoiceNumber());
            dto.setInvoiceDate(formatDate(entity.getInvoiceDate()));
            dto.setSubPoNumber(entity.getSubPoNumber());
            dto.setSubPoDate(formatDate(entity.getSubPoDate()));
            dto.setSubPoQty(entity.getSubPoQty());
            dto.setTotalValueOfPo(entity.getTotalValueOfPo());
            dto.setTcQuantity(entity.getTcQuantity());
            dto.setOfferedQty(entity.getOfferedQty());
            dto.setColorCode(entity.getColorCode());
            dto.setStatus(entity.getStatus());
            dto.setWeightOfferedMt(entity.getWeightOfferedMt());
            dto.setWeightAcceptedMt(entity.getWeightAcceptedMt());
            dto.setWeightRejectedMt(entity.getWeightRejectedMt());
            dto.setCalibrationStatus(entity.getCalibrationStatus());
            dto.setVisualStatus(entity.getVisualStatus());
            dto.setDimensionalStatus(entity.getDimensionalStatus());
            dto.setMaterialTestStatus(entity.getMaterialTestStatus());
            dto.setPackingStatus(entity.getPackingStatus());
            dto.setRemarks(entity.getRemarks());
            dtos.add(dto);
        }

        logger.info("Successfully fetched {} heat final results for call: {}", dtos.size(), callNo);
        return dtos;
    }

    @Override
    public List<RmLadleValuesDto> getLadleValuesByCallNo(String callNo) {
        logger.info("Fetching ladle values (chemical analysis) for call: {}", callNo);

        List<RmChemicalAnalysis> entities = chemicalAnalysisRepository.findByInspectionCallNo(callNo);
        List<RmLadleValuesDto> dtos = new ArrayList<>();

        for (RmChemicalAnalysis entity : entities) {
            RmLadleValuesDto dto = RmLadleValuesDto.builder()
                    .heatNo(entity.getHeatNumber())
                    .percentC(entity.getCarbon())
                    .percentSi(entity.getSilicon())
                    .percentMn(entity.getManganese())
                    .percentP(entity.getPhosphorus())
                    .percentS(entity.getSulphur())
                    .percentCr(entity.getChromium())
                    .build();
            dtos.add(dto);
        }

        logger.info("Successfully fetched {} ladle values for call: {}", dtos.size(), callNo);
        return dtos;
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
