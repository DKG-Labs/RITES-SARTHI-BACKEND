package com.sarthi.controller;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.RmFinishInspectionDto;
import com.sarthi.dto.RmPreInspectionDataDto;
import com.sarthi.dto.RmHeatFinalResultDto;
import com.sarthi.dto.RmLadleValuesDto;
import com.sarthi.entity.RmVisualInspection;
import com.sarthi.repository.RmVisualInspectionRepository;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.service.RmInspectionService;
import com.sarthi.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.math.BigDecimal;

/**
 * REST Controller for Raw Material inspection operations.
 * JWT protected endpoints for IE to save inspection results.
 */
@RestController
@RequestMapping("/api/rm-inspection")
@CrossOrigin(origins = "*")
public class RmInspectionController {

    private static final Logger logger = LoggerFactory.getLogger(RmInspectionController.class);

    @Autowired
    private RmInspectionService service;

    @Autowired
    private RmVisualInspectionRepository visualRepository;

    /**
     * Finish Raw Material inspection - saves all submodule data.
     * Called when inspector clicks "Finish Inspection" button.
     */
    @PostMapping("/finish")
    public ResponseEntity<Object> finishInspection(@RequestBody RmFinishInspectionDto dto) {
        logger.info("POST /api/rm-inspection/finish - Finishing inspection for call: {}", dto.getInspectionCallNo());
        try {
            String result = service.finishInspection(dto);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(result), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Validation errors - return BAD_REQUEST with clear message
            logger.warn("Validation error for RM inspection: {}", e.getMessage());
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.USER_INVALID_INPUT,
                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                AppConstant.ERROR_TYPE_VALIDATION,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error finishing RM inspection: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get complete inspection data by call number.
     */
    @GetMapping("/call/{callNo}")
    public ResponseEntity<Object> getByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/rm-inspection/call/{}", callNo);
        try {
            RmFinishInspectionDto dto = service.getByCallNo(callNo);
            if (dto == null) {
                ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Inspection not found"
                );
                return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching RM inspection: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get cumulative summary data only (pre-inspection data).
     */
    @GetMapping("/summary/{callNo}")
    public ResponseEntity<Object> getSummaryByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/rm-inspection/summary/{}", callNo);
        try {
            RmPreInspectionDataDto dto = service.getSummaryByCallNo(callNo);
            if (dto == null) {
                ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Summary data not found"
                );
                return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dto), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching summary data: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get final inspection results for all heats.
     */
    @GetMapping("/final-results/{callNo}")
    public ResponseEntity<Object> getFinalResultsByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/rm-inspection/final-results/{}", callNo);
        try {
            List<RmHeatFinalResultDto> results = service.getFinalResultsByCallNo(callNo);
            if (results == null || results.isEmpty()) {
                ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Final results not found"
                );
                return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching final results: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get ladle values (chemical analysis from vendor) for all heats.
     * Used in Material Testing page to display ladle values.
     */
    @GetMapping("/ladle-values/{callNo}")
    public ResponseEntity<Object> getLadleValuesByCallNo(@PathVariable String callNo) {
        logger.info("GET /api/rm-inspection/ladle-values/{}", callNo);
        try {
            List<RmLadleValuesDto> ladleValues = service.getLadleValuesByCallNo(callNo);
            if (ladleValues == null || ladleValues.isEmpty()) {
                ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Ladle values not found"
                );
                return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(ladleValues), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching ladle values: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Visual Inspection data for all heats in a call.
     * GET /api/rm-inspection/visual/{callNo}
     * Returns all visual inspection records (both passed and draft)
     */
    @GetMapping("/visual/{callNo}")
    public ResponseEntity<Object> getVisualInspection(@PathVariable String callNo) {
        logger.info("GET /api/rm-inspection/visual/{}", callNo);
        try {
            List<RmVisualInspection> visualData = visualRepository.findByInspectionCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(visualData), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching visual inspection data: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Save Visual Inspection Pass status for a heat.
     * POST /api/rm-inspection/visual/pass
     * Creates or updates records for each selected defect with passed_at and created_by fields.
     * Supports multi-shift: allows re-passing same heat in different shifts.
     */
    @PostMapping("/visual/pass")
    public ResponseEntity<Object> saveVisualInspectionPass(@RequestBody Map<String, Object> passData) {
        logger.info("POST /api/rm-inspection/visual/pass - Marking heat as passed");
        try {
            String callNo = (String) passData.get("inspectionCallNo");
            String heatNo = (String) passData.get("heatNo");
            Integer heatIndex = (Integer) passData.get("heatIndex");
            String createdBy = (String) passData.get("createdBy");

            @SuppressWarnings("unchecked")
            List<String> selectedDefects = (List<String>) passData.get("selectedDefects");

            @SuppressWarnings("unchecked")
            Map<String, Object> defectLengths = (Map<String, Object>) passData.get("defectLengths");

            if (callNo == null || heatNo == null || selectedDefects == null || selectedDefects.isEmpty()) {
                ErrorDetails errorDetails = new ErrorDetails(
                    400,
                    AppConstant.ERROR_TYPE_CODE_INVALID,
                    AppConstant.ERROR_TYPE_INVALID,
                    "Missing required fields: inspectionCallNo, heatNo, or selectedDefects"
                );
                return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.BAD_REQUEST);
            }

            // Create or update records for each selected defect
            List<RmVisualInspection> savedRecords = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            for (String defectName : selectedDefects) {
                // Check if record already exists for this defect
                List<RmVisualInspection> existingRecords = visualRepository.findByInspectionCallNoAndHeatNo(callNo, heatNo);
                RmVisualInspection record = existingRecords.stream()
                    .filter(r -> defectName.equals(r.getDefectName()))
                    .findFirst()
                    .orElse(null);

                if (record == null) {
                    // Create new record
                    record = new RmVisualInspection();
                    record.setInspectionCallNo(callNo);
                    record.setHeatNo(heatNo);
                    record.setHeatIndex(heatIndex);
                    record.setDefectName(defectName);
                    record.setIsSelected(true);
                    record.setCreatedAt(now);
                } else {
                    // Update existing record (for multi-shift support)
                    logger.info("Updating existing record for heat {} defect {}", heatNo, defectName);
                }

                // Set defect length if provided
                if (defectLengths != null && defectLengths.containsKey(defectName)) {
                    Object lengthValue = defectLengths.get(defectName);
                    if (lengthValue != null && !lengthValue.toString().isEmpty()) {
                        try {
                            BigDecimal length = new BigDecimal(lengthValue.toString());
                            record.setDefectLengthMm(length);
                            logger.debug("Set defect length for {} to {}", defectName, length);
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid defect length value for {}: {}", defectName, lengthValue);
                        }
                    }
                }

                // Always update passed_at and createdBy on pass
                record.setPassedAt(now);
                record.setCreatedBy(createdBy);

                RmVisualInspection saved = visualRepository.save(record);
                savedRecords.add(saved);
            }

            logger.info("Successfully saved {} defect records for heat {}", savedRecords.size(), heatNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(savedRecords), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving visual inspection pass status: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Visual Inspection Pass status for all heats in a call.
     * GET /api/rm-inspection/visual/pass/{callNo}
     * Returns all visual inspection records with passed_at timestamp
     */
    @GetMapping("/visual/pass/{callNo}")
    public ResponseEntity<Object> getVisualInspectionPass(@PathVariable String callNo) {
        logger.info("GET /api/rm-inspection/visual/pass/{}", callNo);
        try {
            List<RmVisualInspection> passData = visualRepository.findByInspectionCallNo(callNo)
                .stream()
                .filter(v -> v.getPassedAt() != null)
                .collect(Collectors.toList());
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(passData), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching visual inspection pass status: {}", e.getMessage(), e);
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

