package com.sarthi.service.Impl;

import com.sarthi.dto.processmaterial.*;
import com.sarthi.entity.processmaterial.ProcessLineFinalResult;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.repository.processmaterial.ProcessLineFinalResultRepository;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ProcessInspectionService.
 * Orchestrates existing submodule services to save/retrieve Process Material inspection data.
 * Supports both finish and pause operations with proper audit trail (createdBy/updatedBy).
 */
@Service
public class ProcessInspectionServiceImpl implements ProcessInspectionService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessInspectionServiceImpl.class);

    @Autowired
    private ProcessLineFinalResultRepository lineFinalResultRepository;

    @Autowired
    private InspectionCallRepository inspectionCallRepository;

    @Autowired
    private ProcessCalibrationDocumentsService calibrationService;

    @Autowired
    private ProcessStaticPeriodicCheckService staticCheckService;

    @Autowired
    private ProcessShearingDataService shearingService;

    @Autowired
    private ProcessTurningDataService turningService;

    @Autowired
    private ProcessMpiDataService mpiService;

    @Autowired
    private ProcessForgingDataService forgingService;

    @Autowired
    private ProcessQuenchingDataService quenchingService;

    @Autowired
    private ProcessTemperingDataService temperingService;

    @Autowired
    private ProcessFinalCheckDataService finalCheckService;

    @Autowired
    private ProcessTestingFinishingDataService testingFinishingService;

    @Autowired
    private ProcessOilTankCounterService oilTankService;

    @Override
    @Transactional
    public String finishInspection(ProcessFinishInspectionDto dto, String userId) {
        String callNo = dto.getInspectionCallNo();
        logger.info("Finishing Process Material inspection for call: {} by user: {}", callNo, userId);

        // Save all inspection data
        saveInspectionData(dto, userId);

        // Update Inspection Call Status to COMPLETED
        updateInspectionCallStatus(callNo, "COMPLETED");

        logger.info("Process Material inspection finished successfully for call: {}", callNo);
        return "Process Material Inspection completed successfully";
    }

    @Override
    @Transactional
    public String pauseInspection(ProcessFinishInspectionDto dto, String userId) {
        String callNo = dto.getInspectionCallNo();
        logger.info("Pausing Process Material inspection for call: {} by user: {}", callNo, userId);

        // Save all inspection data without changing status
        saveInspectionData(dto, userId);

        logger.info("Process Material inspection data saved (paused) for call: {}", callNo);
        return "Process Material Inspection data saved successfully";
    }

    /**
     * Core method to save all inspection data using existing submodule services.
     * Sets createdBy and updatedBy fields with userId for audit trail.
     */
    private void saveInspectionData(ProcessFinishInspectionDto dto, String userId) {
        String callNo = dto.getInspectionCallNo();

        if (dto.getLinesData() == null || dto.getLinesData().isEmpty()) {
            logger.warn("No line data provided for call: {}", callNo);
            return;
        }

        for (ProcessLineDataDto lineData : dto.getLinesData()) {
            String lineNo = lineData.getLineNo();
            String poNo = lineData.getPoNo();

            logger.info("Processing line: {} for call: {}", lineNo, callNo);

            // Set audit fields (createdBy/updatedBy) for all DTOs before saving
            // 1. Save Calibration Documents
            if (lineData.getCalibrationDocuments() != null && !lineData.getCalibrationDocuments().isEmpty()) {
                lineData.getCalibrationDocuments().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                calibrationService.saveAll(lineData.getCalibrationDocuments());
            }

            // 2. Save Static Periodic Checks (no saveAll, use individual save)
            if (lineData.getStaticPeriodicChecks() != null && !lineData.getStaticPeriodicChecks().isEmpty()) {
                for (ProcessStaticPeriodicCheckDTO d : lineData.getStaticPeriodicChecks()) {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                    staticCheckService.save(d);
                }
            }

            // 3. Save Shearing Data
            if (lineData.getShearingData() != null && !lineData.getShearingData().isEmpty()) {
                lineData.getShearingData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                shearingService.saveAll(lineData.getShearingData());
            }

            // 4. Save Turning Data
            if (lineData.getTurningData() != null && !lineData.getTurningData().isEmpty()) {
                lineData.getTurningData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                turningService.saveAll(lineData.getTurningData());
            }

            // 5. Save MPI Data
            if (lineData.getMpiData() != null && !lineData.getMpiData().isEmpty()) {
                lineData.getMpiData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                mpiService.saveAll(lineData.getMpiData());
            }

            // 6. Save Forging Data
            if (lineData.getForgingData() != null && !lineData.getForgingData().isEmpty()) {
                lineData.getForgingData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                forgingService.saveAll(lineData.getForgingData());
            }

            // 7. Save Quenching Data
            if (lineData.getQuenchingData() != null && !lineData.getQuenchingData().isEmpty()) {
                lineData.getQuenchingData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                quenchingService.saveAll(lineData.getQuenchingData());
            }

            // 8. Save Tempering Data
            if (lineData.getTemperingData() != null && !lineData.getTemperingData().isEmpty()) {
                lineData.getTemperingData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                temperingService.saveAll(lineData.getTemperingData());
            }

            // 9. Save Final Check Data
            if (lineData.getFinalCheckData() != null && !lineData.getFinalCheckData().isEmpty()) {
                lineData.getFinalCheckData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                finalCheckService.saveAll(lineData.getFinalCheckData());
            }

            // 10. Save Testing & Finishing Data
            if (lineData.getTestingFinishingData() != null && !lineData.getTestingFinishingData().isEmpty()) {
                lineData.getTestingFinishingData().forEach(d -> {
                    d.setInspectionCallNo(callNo);
                    d.setPoNo(poNo);
                    d.setLineNo(lineNo);
                    if (d.getId() == null) d.setCreatedBy(userId);
                    d.setUpdatedBy(userId);
                });
                testingFinishingService.saveAll(lineData.getTestingFinishingData());
            }

            // 11. Save Oil Tank Counter (no saveAll, use individual save)
            if (lineData.getOilTankCounter() != null) {
                ProcessOilTankCounterDTO d = lineData.getOilTankCounter();
                d.setInspectionCallNo(callNo);
                d.setPoNo(poNo);
                d.setLineNo(lineNo);
                if (d.getId() == null) d.setCreatedBy(userId);
                d.setUpdatedBy(userId);
                oilTankService.save(d);
            }

            // 11. Save Line Final Result (Summary with IE remarks, status, quantities)
            // Auto-calculate from submodule data if not provided
            ProcessLineFinalResultDto finalResultDto = lineData.getLineFinalResult();
            if (finalResultDto == null) {
                finalResultDto = calculateLineFinalResult(lineData);
            }

            if (finalResultDto != null) {
                finalResultDto.setInspectionCallNo(callNo);
                finalResultDto.setPoNo(poNo);
                finalResultDto.setLineNo(lineNo);
                if (finalResultDto.getCreatedBy() == null) finalResultDto.setCreatedBy(userId);
                finalResultDto.setUpdatedBy(userId);

                // Convert DTO to Entity
                ProcessLineFinalResult entity = toFinalResultEntity(finalResultDto);

                // Check if a final result already exists for this line
                Optional<ProcessLineFinalResult> existingOpt = lineFinalResultRepository
                        .findByInspectionCallNoAndLineNo(callNo, lineNo);

                if (existingOpt.isPresent()) {
                    // Update existing record
                    ProcessLineFinalResult existing = existingOpt.get();
                    entity.setId(existing.getId());
                    entity.setCreatedAt(existing.getCreatedAt());
                    entity.setCreatedBy(existing.getCreatedBy());
                }

                lineFinalResultRepository.save(entity);
                logger.info("✅ Saved Line Final Result for line: {}", lineNo);
            }
        }
    }

    /**
     * Update inspection call status.
     */
    private void updateInspectionCallStatus(String callNo, String status) {
        Optional<InspectionCall> inspectionOpt = inspectionCallRepository.findByIcNumber(callNo);
        if (inspectionOpt.isPresent()) {
            InspectionCall inspection = inspectionOpt.get();
            inspection.setStatus(status);
            inspectionCallRepository.save(inspection);
            logger.info("Updated inspection call status to {} for call: {}", status, callNo);
        } else {
            logger.warn("Inspection call not found: {}", callNo);
        }
    }

    @Override
    public ProcessFinishInspectionDto getByCallNo(String callNo) {
        logger.info("Fetching Process Material inspection data for call: {}", callNo);

        ProcessFinishInspectionDto dto = new ProcessFinishInspectionDto();
        dto.setInspectionCallNo(callNo);

        // Get all lines for this inspection call
        // We need to determine which lines exist by querying one of the submodule tables
        List<ProcessCalibrationDocumentsDTO> allCalibrations = calibrationService.getByInspectionCallNo(callNo);

        // Extract unique line numbers
        List<String> lineNumbers = allCalibrations.stream()
                .map(ProcessCalibrationDocumentsDTO::getLineNo)
                .distinct()
                .collect(Collectors.toList());

        List<ProcessLineDataDto> linesData = new ArrayList<>();

        for (String lineNo : lineNumbers) {
            // Get PO number from calibration data
            String poNo = allCalibrations.stream()
                    .filter(c -> c.getLineNo().equals(lineNo))
                    .findFirst()
                    .map(ProcessCalibrationDocumentsDTO::getPoNo)
                    .orElse(null);

            ProcessLineDataDto lineData = new ProcessLineDataDto();
            lineData.setLineNo(lineNo);
            lineData.setPoNo(poNo);
            lineData.setInspectionCallNo(callNo);

            // Fetch all submodule data for this line
            lineData.setCalibrationDocuments(calibrationService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setShearingData(shearingService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setTurningData(turningService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setMpiData(mpiService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setForgingData(forgingService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setQuenchingData(quenchingService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setTemperingData(temperingService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));
            lineData.setFinalCheckData(finalCheckService.getByCallNoPoNoLineNo(callNo, poNo, lineNo));

            // Oil Tank Counter and Static Checks return Optional
            oilTankService.getByCallNoPoNoLineNo(callNo, poNo, lineNo).ifPresent(lineData::setOilTankCounter);
            staticCheckService.getByCallNoPoNoLineNo(callNo, poNo, lineNo).ifPresent(check -> {
                List<ProcessStaticPeriodicCheckDTO> checks = new ArrayList<>();
                checks.add(check);
                lineData.setStaticPeriodicChecks(checks);
            });

            // Fetch Line Final Result (Summary with IE remarks, status, quantities)
            lineFinalResultRepository.findByInspectionCallNoAndLineNo(callNo, lineNo)
                    .ifPresent(entity -> lineData.setLineFinalResult(toFinalResultDto(entity)));

            linesData.add(lineData);
        }

        dto.setLinesData(linesData);

        logger.info("Fetched data for {} lines for call: {}", linesData.size(), callNo);
        return dto;
    }

    @Override
    public List<ProcessLineFinalResultDto> getFinalResultsByCallNo(String callNo) {
        logger.info("Fetching final results for call: {}", callNo);

        List<ProcessLineFinalResult> entities = lineFinalResultRepository.findByInspectionCallNo(callNo);

        return entities.stream()
                .map(this::toFinalResultDto)
                .collect(Collectors.toList());
    }

    private ProcessLineFinalResultDto toFinalResultDto(ProcessLineFinalResult entity) {
        ProcessLineFinalResultDto dto = new ProcessLineFinalResultDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private ProcessLineFinalResult toFinalResultEntity(ProcessLineFinalResultDto dto) {
        ProcessLineFinalResult entity = new ProcessLineFinalResult();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    /**
     * Auto-calculate line final result from submodule data.
     * Aggregates quantities and statuses from all process stages.
     */
    private ProcessLineFinalResultDto calculateLineFinalResult(ProcessLineDataDto lineData) {
        ProcessLineFinalResultDto result = new ProcessLineFinalResultDto();

        // Initialize counters
        int totalAccepted = 0;
        int totalRejected = 0;

        // Aggregate from Shearing Data (only has rejected quantities)
        if (lineData.getShearingData() != null && !lineData.getShearingData().isEmpty()) {
            for (ProcessShearingDataDTO data : lineData.getShearingData()) {
                if (data.getRejectedQty1() != null) totalRejected += data.getRejectedQty1();
                if (data.getRejectedQty2() != null) totalRejected += data.getRejectedQty2();
            }
            result.setShearingStatus("COMPLETED");
        }

        // Aggregate from Turning Data
        if (lineData.getTurningData() != null && !lineData.getTurningData().isEmpty()) {
            for (ProcessTurningDataDTO data : lineData.getTurningData()) {
                if (data.getAcceptedQty() != null) totalAccepted += data.getAcceptedQty();
                if (data.getRejectedQty1() != null) totalRejected += data.getRejectedQty1();
                if (data.getRejectedQty2() != null) totalRejected += data.getRejectedQty2();
            }
            result.setTurningStatus("COMPLETED");
        }

        // Aggregate from MPI Data
        if (lineData.getMpiData() != null && !lineData.getMpiData().isEmpty()) {
            for (ProcessMpiDataDTO data : lineData.getMpiData()) {
                if (data.getRejectedQty1() != null) totalRejected += data.getRejectedQty1();
                if (data.getRejectedQty2() != null) totalRejected += data.getRejectedQty2();
            }
            result.setMpiStatus("COMPLETED");
        }

        // Aggregate from Forging Data
        if (lineData.getForgingData() != null && !lineData.getForgingData().isEmpty()) {
            for (ProcessForgingDataDTO data : lineData.getForgingData()) {
                if (data.getAcceptedQty() != null) totalAccepted += data.getAcceptedQty();
                if (data.getRejectedQty() != null) totalRejected += data.getRejectedQty();
            }
            result.setForgingStatus("COMPLETED");
        }

        // Aggregate from Quenching Data
        if (lineData.getQuenchingData() != null && !lineData.getQuenchingData().isEmpty()) {
            for (ProcessQuenchingDataDTO data : lineData.getQuenchingData()) {
                if (data.getRejectedQty() != null) totalRejected += data.getRejectedQty();
            }
            result.setQuenchingStatus("COMPLETED");
        }

        // Aggregate from Tempering Data
        if (lineData.getTemperingData() != null && !lineData.getTemperingData().isEmpty()) {
            for (ProcessTemperingDataDTO data : lineData.getTemperingData()) {
                if (data.getAcceptedQty() != null) totalAccepted += data.getAcceptedQty();
                if (data.getRejectedQty() != null) totalRejected += data.getRejectedQty();
            }
            result.setTemperingStatus("COMPLETED");
        }

        // Aggregate from Final Check Data
        if (lineData.getFinalCheckData() != null && !lineData.getFinalCheckData().isEmpty()) {
            for (ProcessFinalCheckDataDTO data : lineData.getFinalCheckData()) {
                if (data.getRejectedNo1() != null) totalRejected += data.getRejectedNo1();
                if (data.getRejectedNo2() != null) totalRejected += data.getRejectedNo2();
                if (data.getRejectedNo3() != null) totalRejected += data.getRejectedNo3();
            }
            result.setFinalCheckStatus("COMPLETED");
        }

        // Set calibration and static check status
        if (lineData.getCalibrationDocuments() != null && !lineData.getCalibrationDocuments().isEmpty()) {
            result.setCalibrationStatus("COMPLETED");
        }
        if (lineData.getStaticPeriodicChecks() != null && !lineData.getStaticPeriodicChecks().isEmpty()) {
            result.setStaticCheckStatus("COMPLETED");
        }

        // Set totals
        result.setTotalAccepted(totalAccepted);
        result.setTotalRejected(totalRejected);

        // Determine overall status
        if (totalRejected == 0 && totalAccepted > 0) {
            result.setStatus("ACCEPTED");
            result.setOverallStatus("ACCEPTED");
        } else if (totalRejected > 0 && totalAccepted > 0) {
            result.setStatus("PARTIALLY_ACCEPTED");
            result.setOverallStatus("PARTIALLY_ACCEPTED");
        } else if (totalRejected > 0 && totalAccepted == 0) {
            result.setStatus("REJECTED");
            result.setOverallStatus("REJECTED");
        } else {
            result.setStatus("PENDING");
            result.setOverallStatus("PENDING");
        }

        result.setRemarks("Auto-calculated from submodule data");

        logger.info("Auto-calculated line final result: Accepted={}, Rejected={}, Status={}",
                    totalAccepted, totalRejected, result.getStatus());

        return result;
    }
}
