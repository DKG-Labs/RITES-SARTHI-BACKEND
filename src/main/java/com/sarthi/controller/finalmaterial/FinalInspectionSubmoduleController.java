package com.sarthi.controller.finalmaterial;

import com.sarthi.entity.finalmaterial.*;
import com.sarthi.service.finalmaterial.FinalInspectionSubmoduleService;
import com.sarthi.dto.finalmaterial.FinalLadleValuesDto;
import com.sarthi.dto.finalmaterial.FinalInclusionRatingBatchDTO;
import com.sarthi.util.ResponseBuilder;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.constant.AppConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST Controller for Final Inspection Submodules
 * Handles CRUD operations for all final inspection submodule data
 */
@RestController
@RequestMapping("/api/final-inspection/submodules")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Final Inspection Submodules", description = "APIs for Final Inspection Submodule data management")
public class FinalInspectionSubmoduleController {

    private static final Logger logger = LoggerFactory.getLogger(FinalInspectionSubmoduleController.class);
    private final FinalInspectionSubmoduleService submoduleService;

    /**
     * Helper method to get userId from payload or principal
     * Priority: payload createdBy > principal > "system"
     */
    private String getUserId(Object data, Principal principal) {
        try {
            // Try to get createdBy from the entity using reflection
            java.lang.reflect.Field createdByField = data.getClass().getDeclaredField("createdBy");
            createdByField.setAccessible(true);
            String createdBy = (String) createdByField.get(data);
            if (createdBy != null && !createdBy.isEmpty()) {
                return createdBy;
            }
        } catch (Exception e) {
            // Field doesn't exist or can't be accessed, continue to next option
        }

        // Fallback to principal
        if (principal != null) {
            return principal.getName();
        }

        // Final fallback
        return "system";
    }

    // ===== CALIBRATION & DOCUMENTS =====
    @PostMapping("/calibration-documents")
    @Operation(summary = "Save Calibration & Documents data")
    public ResponseEntity<?> saveCalibrationDocuments(
            @RequestBody FinalCalibrationDocuments data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalCalibrationDocuments saved = submoduleService.saveCalibrationDocuments(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving calibration documents", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save calibration documents: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calibration-documents/call/{callNo}")
    @Operation(summary = "Get Calibration & Documents by Call Number")
    public ResponseEntity<?> getCalibrationDocumentsByCall(@PathVariable String callNo) {
        try {
            List<FinalCalibrationDocuments> data = submoduleService.getCalibrationDocumentsByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving calibration documents", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve calibration documents: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calibration-documents/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Calibration & Documents by Call and Lot Number")
    public ResponseEntity<?> getCalibrationDocumentsByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalCalibrationDocuments> data = submoduleService.getCalibrationDocumentsByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving calibration documents", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve calibration documents: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/calibration-documents/{id}")
    @Operation(summary = "Update Calibration & Documents data")
    public ResponseEntity<?> updateCalibrationDocuments(
            @PathVariable Long id,
            @RequestBody FinalCalibrationDocuments data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalCalibrationDocuments updated = submoduleService.updateCalibrationDocuments(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating calibration documents", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update calibration documents: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/calibration-documents/{id}")
    @Operation(summary = "Delete Calibration & Documents data")
    public ResponseEntity<?> deleteCalibrationDocuments(@PathVariable Long id) {
        try {
            submoduleService.deleteCalibrationDocuments(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Calibration documents deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting calibration documents", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete calibration documents: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== VISUAL & DIMENSIONAL =====
    @PostMapping("/visual-dimensional")
    @Operation(summary = "Save Visual & Dimensional data")
    public ResponseEntity<?> saveVisualDimensional(
            @RequestBody FinalVisualDimensional data,
            Principal principal) {
        try {
            // Use createdBy from payload if provided, otherwise use principal or fallback to "system"
            String userId = (data.getCreatedBy() != null && !data.getCreatedBy().isEmpty()) ?
                            data.getCreatedBy() :
                            (principal != null ? principal.getName() : "system");
            FinalVisualDimensional saved = submoduleService.saveVisualDimensional(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving visual dimensional data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save visual dimensional data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/visual-dimensional/call/{callNo}")
    @Operation(summary = "Get Visual & Dimensional by Call Number")
    public ResponseEntity<?> getVisualDimensionalByCall(@PathVariable String callNo) {
        try {
            List<FinalVisualDimensional> data = submoduleService.getVisualDimensionalByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving visual dimensional data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve visual dimensional data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/visual-dimensional/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Visual & Dimensional by Call and Lot Number")
    public ResponseEntity<?> getVisualDimensionalByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalVisualDimensional> data = submoduleService.getVisualDimensionalByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving visual dimensional data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve visual dimensional data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/visual-dimensional/{id}")
    @Operation(summary = "Update Visual & Dimensional data")
    public ResponseEntity<?> updateVisualDimensional(
            @PathVariable Long id,
            @RequestBody FinalVisualDimensional data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalVisualDimensional updated = submoduleService.updateVisualDimensional(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating visual dimensional data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update visual dimensional data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/visual-dimensional/{id}")
    @Operation(summary = "Delete Visual & Dimensional data")
    public ResponseEntity<?> deleteVisualDimensional(@PathVariable Long id) {
        try {
            submoduleService.deleteVisualDimensional(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Visual & Dimensional data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting visual dimensional data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete visual dimensional data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== CHEMICAL ANALYSIS =====
    @PostMapping("/chemical-analysis")
    @Operation(summary = "Save Chemical Analysis data")
    public ResponseEntity<?> saveChemicalAnalysis(
            @RequestBody FinalChemicalAnalysis data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalChemicalAnalysis saved = submoduleService.saveChemicalAnalysis(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving chemical analysis data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save chemical analysis data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chemical-analysis/call/{callNo}")
    @Operation(summary = "Get Chemical Analysis by Call Number")
    public ResponseEntity<?> getChemicalAnalysisByCall(@PathVariable String callNo) {
        try {
            List<FinalChemicalAnalysis> data = submoduleService.getChemicalAnalysisByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving chemical analysis data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve chemical analysis data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chemical-analysis/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Chemical Analysis by Call and Lot Number")
    public ResponseEntity<?> getChemicalAnalysisByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalChemicalAnalysis> data = submoduleService.getChemicalAnalysisByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving chemical analysis data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve chemical analysis data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/chemical-analysis/{id}")
    @Operation(summary = "Update Chemical Analysis data")
    public ResponseEntity<?> updateChemicalAnalysis(
            @PathVariable Long id,
            @RequestBody FinalChemicalAnalysis data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalChemicalAnalysis updated = submoduleService.updateChemicalAnalysis(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating chemical analysis data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update chemical analysis data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/chemical-analysis/{id}")
    @Operation(summary = "Delete Chemical Analysis data")
    public ResponseEntity<?> deleteChemicalAnalysis(@PathVariable Long id) {
        try {
            submoduleService.deleteChemicalAnalysis(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Chemical analysis data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting chemical analysis data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete chemical analysis data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== HARDNESS TEST =====
    @PostMapping("/hardness-test")
    @Operation(summary = "Save Hardness Test data")
    public ResponseEntity<?> saveHardnessTest(
            @RequestBody FinalHardnessTest data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalHardnessTest saved = submoduleService.saveHardnessTest(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving hardness test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save hardness test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hardness-test/call/{callNo}")
    @Operation(summary = "Get Hardness Test by Call Number")
    public ResponseEntity<?> getHardnessTestByCall(@PathVariable String callNo) {
        try {
            List<FinalHardnessTest> data = submoduleService.getHardnessTestByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving hardness test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve hardness test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hardness-test/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Hardness Test by Call and Lot Number")
    public ResponseEntity<?> getHardnessTestByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalHardnessTest> data = submoduleService.getHardnessTestByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving hardness test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve hardness test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hardness-test/{id}")
    @Operation(summary = "Update Hardness Test data")
    public ResponseEntity<?> updateHardnessTest(
            @PathVariable Long id,
            @RequestBody FinalHardnessTest data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalHardnessTest updated = submoduleService.updateHardnessTest(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating hardness test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update hardness test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hardness-test/{id}")
    @Operation(summary = "Delete Hardness Test data")
    public ResponseEntity<?> deleteHardnessTest(@PathVariable Long id) {
        try {
            submoduleService.deleteHardnessTest(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Hardness test data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting hardness test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete hardness test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== INCLUSION & DECARB =====
    @PostMapping("/inclusion-rating")
    @Operation(summary = "Save Inclusion Rating data")
    public ResponseEntity<?> saveInclusionRating(
            @RequestBody FinalInclusionRating data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalInclusionRating saved = submoduleService.saveInclusionRating(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving inclusion rating data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save inclusion rating data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/inclusion-rating/batch")
    @Operation(summary = "Save Inclusion Rating data for multiple samples in a lot")
    public ResponseEntity<?> saveInclusionRatingBatch(
            @RequestBody FinalInclusionRatingBatchDTO batchData,
            Principal principal) {
        try {
            String userId = getUserId(batchData, principal);
            List<FinalInclusionRating> saved = submoduleService.saveInclusionRatingBatch(batchData, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving inclusion rating batch data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save inclusion rating batch data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inclusion-rating/call/{callNo}")
    @Operation(summary = "Get Inclusion Rating by Call Number")
    public ResponseEntity<?> getInclusionRatingByCall(@PathVariable String callNo) {
        try {
            List<FinalInclusionRating> data = submoduleService.getInclusionRatingByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving inclusion rating data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve inclusion rating data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inclusion-rating/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Inclusion Rating by Call and Lot Number")
    public ResponseEntity<?> getInclusionRatingByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalInclusionRating> data = submoduleService.getInclusionRatingByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving inclusion rating data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve inclusion rating data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/inclusion-rating/{id}")
    @Operation(summary = "Update Inclusion Rating data")
    public ResponseEntity<?> updateInclusionRating(
            @PathVariable Long id,
            @RequestBody FinalInclusionRating data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalInclusionRating updated = submoduleService.updateInclusionRating(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating inclusion rating data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update inclusion rating data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/inclusion-rating/{id}")
    @Operation(summary = "Delete Inclusion Rating data")
    public ResponseEntity<?> deleteInclusionRating(@PathVariable Long id) {
        try {
            submoduleService.deleteInclusionRating(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Inclusion rating data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting inclusion rating data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete inclusion rating data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== APPLICATION DEFLECTION =====
    @PostMapping("/application-deflection")
    @Operation(summary = "Save Application Deflection data")
    public ResponseEntity<?> saveApplicationDeflection(
            @RequestBody FinalApplicationDeflection data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalApplicationDeflection saved = submoduleService.saveApplicationDeflection(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving application deflection data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save application deflection data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/application-deflection/call/{callNo}")
    @Operation(summary = "Get Application Deflection by Call Number")
    public ResponseEntity<?> getApplicationDeflectionByCall(@PathVariable String callNo) {
        try {
            List<FinalApplicationDeflection> data = submoduleService.getApplicationDeflectionByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving application deflection data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve application deflection data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/application-deflection/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Application Deflection by Call and Lot Number")
    public ResponseEntity<?> getApplicationDeflectionByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalApplicationDeflection> data = submoduleService.getApplicationDeflectionByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving application deflection data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve application deflection data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/application-deflection/{id}")
    @Operation(summary = "Update Application Deflection data")
    public ResponseEntity<?> updateApplicationDeflection(
            @PathVariable Long id,
            @RequestBody FinalApplicationDeflection data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalApplicationDeflection updated = submoduleService.updateApplicationDeflection(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating application deflection data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update application deflection data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/application-deflection/{id}")
    @Operation(summary = "Delete Application Deflection data")
    public ResponseEntity<?> deleteApplicationDeflection(@PathVariable Long id) {
        try {
            submoduleService.deleteApplicationDeflection(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Application deflection data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting application deflection data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete application deflection data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== WEIGHT TEST =====
    @PostMapping("/weight-test")
    @Operation(summary = "Save Weight Test data")
    public ResponseEntity<?> saveWeightTest(
            @RequestBody FinalWeightTest data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalWeightTest saved = submoduleService.saveWeightTest(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving weight test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save weight test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/weight-test/call/{callNo}")
    @Operation(summary = "Get Weight Test by Call Number")
    public ResponseEntity<?> getWeightTestByCall(@PathVariable String callNo) {
        try {
            List<FinalWeightTest> data = submoduleService.getWeightTestByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving weight test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve weight test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/weight-test/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Weight Test by Call and Lot Number")
    public ResponseEntity<?> getWeightTestByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalWeightTest> data = submoduleService.getWeightTestByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving weight test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve weight test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/weight-test/{id}")
    @Operation(summary = "Update Weight Test data")
    public ResponseEntity<?> updateWeightTest(
            @PathVariable Long id,
            @RequestBody FinalWeightTest data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalWeightTest updated = submoduleService.updateWeightTest(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating weight test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update weight test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/weight-test/{id}")
    @Operation(summary = "Delete Weight Test data")
    public ResponseEntity<?> deleteWeightTest(@PathVariable Long id) {
        try {
            submoduleService.deleteWeightTest(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Weight test data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting weight test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete weight test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== TOE LOAD TEST =====
    @PostMapping("/toe-load-test")
    @Operation(summary = "Save Toe Load Test data")
    public ResponseEntity<?> saveToeLoadTest(
            @RequestBody FinalToeLoadTest data,
            Principal principal) {
        try {
            String userId = getUserId(data, principal);
            FinalToeLoadTest saved = submoduleService.saveToeLoadTest(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving toe load test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to save toe load test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/toe-load-test/call/{callNo}")
    @Operation(summary = "Get Toe Load Test by Call Number")
    public ResponseEntity<?> getToeLoadTestByCall(@PathVariable String callNo) {
        try {
            List<FinalToeLoadTest> data = submoduleService.getToeLoadTestByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving toe load test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve toe load test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/toe-load-test/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get Toe Load Test by Call and Lot Number")
    public ResponseEntity<?> getToeLoadTestByLot(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        try {
            List<FinalToeLoadTest> data = submoduleService.getToeLoadTestByLot(callNo, lotNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving toe load test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve toe load test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/toe-load-test/{id}")
    @Operation(summary = "Update Toe Load Test data")
    public ResponseEntity<?> updateToeLoadTest(
            @PathVariable Long id,
            @RequestBody FinalToeLoadTest data,
            Principal principal) {
        try {
            String userId = principal.getName();
            data.setId(id);
            FinalToeLoadTest updated = submoduleService.updateToeLoadTest(data, userId);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating toe load test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to update toe load test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/toe-load-test/{id}")
    @Operation(summary = "Delete Toe Load Test data")
    public ResponseEntity<?> deleteToeLoadTest(@PathVariable Long id) {
        try {
            submoduleService.deleteToeLoadTest(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Toe load test data deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting toe load test data", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to delete toe load test data: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===== LADLE VALUES =====
    @GetMapping("/ladle-values/{callNo}")
    @Operation(summary = "Get Ladle Values by Call Number")
    public ResponseEntity<?> getLadleValuesByCall(@PathVariable String callNo) {
        try {
            List<FinalLadleValuesDto> data = submoduleService.getLadleValuesByCallNo(callNo);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(data), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving ladle values", e);
            ErrorDetails errorDetails = new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Failed to retrieve ladle values: " + e.getMessage()
            );
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

