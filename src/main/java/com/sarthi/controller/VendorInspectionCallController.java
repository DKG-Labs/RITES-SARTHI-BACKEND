package com.sarthi.controller;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.VendorInspectionCallStatusDto;
import com.sarthi.service.VendorInspectionCallService;
import com.sarthi.util.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Vendor Inspection Call operations.
 * Provides endpoints for vendors to view their inspection calls with workflow status.
 */
@RestController
@RequestMapping("/api/vendor/inspection-calls")
@CrossOrigin(origins = "*")
@Tag(name = "Vendor Inspection Calls", description = "APIs for vendor inspection call management")
public class VendorInspectionCallController {

    private static final Logger logger = LoggerFactory.getLogger(VendorInspectionCallController.class);

    @Autowired
    private VendorInspectionCallService vendorInspectionCallService;

    /**
     * Get all inspection calls for a vendor with workflow status.
     * GET /api/vendor/inspection-calls/status?vendorId={vendorId}
     * 
     * @param vendorId Vendor ID to filter inspection calls
     * @return List of inspection calls with workflow status
     */
    @GetMapping("/status")
    @Operation(
        summary = "Get vendor inspection calls with workflow status",
        description = "Fetches all inspection calls for a vendor with their latest workflow transition status"
    )
    public ResponseEntity<Object> getVendorInspectionCallsWithStatus(
            @RequestParam String vendorId) {
        
        logger.info("Received request to fetch inspection calls with status for vendor: {}", vendorId);
        
        try {
            List<VendorInspectionCallStatusDto> calls = 
                vendorInspectionCallService.getVendorInspectionCallsWithStatus(vendorId);
            
            logger.info("Successfully fetched {} inspection calls for vendor: {}", calls.size(), vendorId);
            
            return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(calls), 
                HttpStatus.OK
            );
        } catch (Exception e) {
            logger.error("Error fetching inspection calls for vendor: {}", vendorId, e);
            return new ResponseEntity<>(
                ResponseBuilder.getErrorResponse(
                    new com.sarthi.exception.ErrorDetails(
                        AppConstant.INTER_SERVER_ERROR,
                        AppConstant.ERROR_TYPE_CODE_INTERNAL,
                        AppConstant.ERROR_TYPE_ERROR,
                        "Error fetching inspection calls: " + e.getMessage()
                    )
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

