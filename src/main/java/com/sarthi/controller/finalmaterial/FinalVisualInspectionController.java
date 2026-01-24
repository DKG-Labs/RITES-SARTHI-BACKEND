package com.sarthi.controller.finalmaterial;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.finalmaterial.FinalVisualInspectionDto;
import com.sarthi.entity.finalmaterial.FinalVisualInspection;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.service.finalmaterial.FinalVisualInspectionService;
import com.sarthi.util.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Final Visual Inspection
 * Handles API endpoints for visual inspection data
 */
@RestController
@RequestMapping("/api/final-material/visual-inspection")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Final Visual Inspection", description = "APIs for Final Product Visual Inspection")
public class FinalVisualInspectionController {

    private final FinalVisualInspectionService service;

    @PostMapping
    @Operation(summary = "Save visual inspection", description = "Creates a new visual inspection record")
    public ResponseEntity<Object> saveVisualInspection(
            @RequestBody FinalVisualInspectionDto dto,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        log.info("POST /api/final-material/visual-inspection - Saving visual inspection for call: {}", dto.getInspectionCallNo());
        try {
            // Priority: createdBy from DTO > X-User-Id header > SYSTEM
            String userIdToUse = dto.getCreatedBy() != null && !dto.getCreatedBy().isEmpty()
                    ? dto.getCreatedBy()
                    : (userId != null ? userId : "SYSTEM");
            FinalVisualInspection result = service.saveVisualInspection(dto, userIdToUse);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(result), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error saving visual inspection", e);
            ErrorDetails errorDetails = new ErrorDetails(AppConstant.INTER_SERVER_ERROR, AppConstant.ERROR_TYPE_CODE_INTERNAL,
                    AppConstant.ERROR_TYPE_ERROR, e.getMessage());
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get visual inspection by ID", description = "Retrieves a visual inspection record by ID")
    public ResponseEntity<Object> getVisualInspectionById(@PathVariable Long id) {
        log.info("GET /api/final-material/visual-inspection/{} - Fetching visual inspection", id);
        return service.getVisualInspectionById(id)
                .map(result -> new ResponseEntity<>((Object) ResponseBuilder.getSuccessResponse(result), HttpStatus.OK))
                .orElse(new ResponseEntity<>((Object) ResponseBuilder.getErrorResponse(
                        new ErrorDetails(AppConstant.INTER_SERVER_ERROR, AppConstant.ERROR_TYPE_CODE_INTERNAL,
                                AppConstant.ERROR_TYPE_ERROR, "Visual inspection not found")), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/call/{callNo}")
    @Operation(summary = "Get visual inspections by call number", description = "Retrieves all visual inspection records for a call")
    public ResponseEntity<Object> getVisualInspectionByCallNo(@PathVariable String callNo) {
        log.info("GET /api/final-material/visual-inspection/call/{} - Fetching visual inspections", callNo);
        List<FinalVisualInspection> results = service.getVisualInspectionByCallNo(callNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
    }

    @GetMapping("/call/{callNo}/lot/{lotNo}")
    @Operation(summary = "Get visual inspection by call and lot", description = "Retrieves visual inspection for specific call and lot")
    public ResponseEntity<Object> getVisualInspectionByCallNoAndLotNo(
            @PathVariable String callNo,
            @PathVariable String lotNo) {
        log.info("GET /api/final-material/visual-inspection/call/{}/lot/{}", callNo, lotNo);
        return service.getVisualInspectionByCallNoAndLotNo(callNo, lotNo)
                .map(result -> new ResponseEntity<>((Object) ResponseBuilder.getSuccessResponse(result), HttpStatus.OK))
                .orElse(new ResponseEntity<>((Object) ResponseBuilder.getErrorResponse(
                        new ErrorDetails(AppConstant.INTER_SERVER_ERROR, AppConstant.ERROR_TYPE_CODE_INTERNAL,
                                AppConstant.ERROR_TYPE_ERROR, "Visual inspection not found")), HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @Operation(summary = "Update visual inspection", description = "Updates an existing visual inspection record")
    public ResponseEntity<Object> updateVisualInspection(
            @RequestBody FinalVisualInspectionDto dto,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        log.info("PUT /api/final-material/visual-inspection - Updating visual inspection for call: {}", dto.getInspectionCallNo());
        try {
            FinalVisualInspection result = service.updateVisualInspection(dto, userId != null ? userId : "SYSTEM");
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(result), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating visual inspection", e);
            ErrorDetails errorDetails = new ErrorDetails(AppConstant.INTER_SERVER_ERROR, AppConstant.ERROR_TYPE_CODE_INTERNAL,
                    AppConstant.ERROR_TYPE_ERROR, e.getMessage());
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete visual inspection", description = "Deletes a visual inspection record")
    public ResponseEntity<Object> deleteVisualInspection(@PathVariable Long id) {
        log.info("DELETE /api/final-material/visual-inspection/{}", id);
        try {
            service.deleteVisualInspection(id);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Visual inspection deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting visual inspection", e);
            ErrorDetails errorDetails = new ErrorDetails(AppConstant.INTER_SERVER_ERROR, AppConstant.ERROR_TYPE_CODE_INTERNAL,
                    AppConstant.ERROR_TYPE_ERROR, e.getMessage());
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.BAD_REQUEST);
        }
    }
}

