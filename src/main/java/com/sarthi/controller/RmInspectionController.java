package com.sarthi.controller;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.RmFinishInspectionDto;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.service.RmInspectionService;
import com.sarthi.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

