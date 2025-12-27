package com.sarthi.controller.rawmaterial;

import com.sarthi.dto.IcDtos.InspectionCallRequestDto;
import com.sarthi.dto.IcDtos.RmInspectionDetailsRequestDto;
import com.sarthi.dto.rawmaterial.*;
import com.sarthi.dto.vendorDtos.VendorPoHeaderResponseDto;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.constant.AppConstant;
import com.sarthi.dto.IcDtos.CreateInspectionCallRequestDto;
import com.sarthi.dto.rawmaterial.*;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.service.InspectionCallService;
import com.sarthi.service.rawmaterial.RawMaterialInspectionService;
import com.sarthi.util.APIResponse;
import com.sarthi.util.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Raw Material Inspection operations.
 * All endpoints are JWT protected.
 *
 * Base URL: /api/raw-material
 */
@RestController
@RequestMapping("/api/raw-material")
@CrossOrigin(origins = "*")
@Tag(name = "Raw Material Inspection", description = "APIs for Raw Material inspection data")
@SecurityRequirement(name = "bearerAuth")
public class RawMaterialInspectionController {

    private static final Logger logger = LoggerFactory.getLogger(RawMaterialInspectionController.class);

    private final RawMaterialInspectionService rmService;

    @Autowired
    private InspectionCallService inspectionCallService;

    @Autowired
    public RawMaterialInspectionController(RawMaterialInspectionService rmService) {
        this.rmService = rmService;
    }

    /* ==================== Inspection Call Endpoints ==================== */

    /**
     * Get all Raw Material inspection calls
     * GET /api/raw-material/calls
     */
    @GetMapping("/calls")
    @Operation(summary = "Get all RM inspection calls", description = "Fetches all Raw Material inspection calls with basic details")
    public ResponseEntity<APIResponse> getAllRawMaterialCalls() {
        logger.info("Request: Get all Raw Material inspection calls");
        List<InspectionCallDto> calls = rmService.getAllRawMaterialCalls();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(calls), HttpStatus.OK);
    }

    /**
     * Get Raw Material calls by status
     * GET /api/raw-material/calls/status/{status}
     */
    @GetMapping("/calls/status/{status}")
    @Operation(summary = "Get RM calls by status", description = "Fetches Raw Material calls filtered by status")
    public ResponseEntity<APIResponse> getRawMaterialCallsByStatus(@PathVariable String status) {
        logger.info("Request: Get Raw Material calls by status: {}", status);
        List<InspectionCallDto> calls = rmService.getRawMaterialCallsByStatus(status.toUpperCase());
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(calls), HttpStatus.OK);
    }

    /**
     * Get inspection call by ID (with full details)
     * GET /api/raw-material/calls/{id}
     */
    @GetMapping("/calls/{id}")
    @Operation(summary = "Get call by ID", description = "Fetches complete inspection call details including heats")
    public ResponseEntity<APIResponse> getInspectionCallById(@PathVariable Integer id) {
        logger.info("Request: Get inspection call by ID: {}", id);
        InspectionCallDto call = rmService.getInspectionCallById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(call), HttpStatus.OK);
    }

    /**
     * Get inspection call by IC number
     * GET /api/raw-material/calls/ic-number/{icNumber}
     */
    @GetMapping("/calls/ic-number/{icNumber}")
    @Operation(summary = "Get call by IC number", description = "Fetches inspection call by unique IC number")
    public ResponseEntity<APIResponse> getInspectionCallByIcNumber(@PathVariable String icNumber) {
        logger.info("Request: Get inspection call by IC number: {}", icNumber);
        InspectionCallDto call = rmService.getInspectionCallByIcNumber(icNumber);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(call), HttpStatus.OK);
    }

    /* ==================== RM Inspection Details Endpoints ==================== */

    /**
     * Get RM inspection details by call ID
     * GET /api/raw-material/details/call/{callId}
     */
    @GetMapping("/details/call/{callId}")
    @Operation(summary = "Get RM details by call ID", description = "Fetches RM-specific inspection details for a call")
    public ResponseEntity<APIResponse> getRmDetailsByCallId(@PathVariable Integer callId) {
        logger.info("Request: Get RM details for call ID: {}", callId);
        RmInspectionDetailsDto details = rmService.getRmDetailsByCallId(callId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(details), HttpStatus.OK);
    }

    /* ==================== Heat Quantity Endpoints ==================== */

    /**
     * Get all heat quantities for an RM detail
     * GET /api/raw-material/heats/rm-detail/{rmDetailId}
     */
    @GetMapping("/heats/rm-detail/{rmDetailId}")
    @Operation(summary = "Get heats by RM detail ID", description = "Fetches all heat-wise quantity breakdown for an RM inspection detail")
    public ResponseEntity<APIResponse> getHeatQuantitiesByRmDetailId(@PathVariable Integer rmDetailId) {
        logger.info("Request: Get heat quantities for RM detail ID: {}", rmDetailId);
        List<RmHeatQuantityDto> heats = rmService.getHeatQuantitiesByRmDetailId(rmDetailId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(heats), HttpStatus.OK);
    }

    /**
     * Get heat quantity by ID
     * GET /api/raw-material/heats/{heatId}
     */
    @GetMapping("/heats/{heatId}")
    @Operation(summary = "Get heat by ID", description = "Fetches heat quantity details")
    public ResponseEntity<APIResponse> getHeatQuantityById(@PathVariable Integer heatId) {
        logger.info("Request: Get heat quantity by ID: {}", heatId);
        RmHeatQuantityDto heat = rmService.getHeatQuantityById(heatId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(heat), HttpStatus.OK);
    }




    // @PostMapping("/inspectionCall")
    // public ResponseEntity<Object> createInspectionCall(@RequestBody InspectionCallRequestDto icRequest,
    //                                                  @RequestBody  RmInspectionDetailsRequestDto rmRequest) {

    //   InspectionCall ic =  inspectionCallService.createInspectionCall(icRequest, rmRequest);
    //     return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(ic), HttpStatus.OK);


    /**
     * Create a new inspection call with RM details
     * POST /api/raw-material/inspectionCall
     */
    @PostMapping("/inspectionCall")
    @Operation(summary = "Create inspection call", description = "Creates a new inspection call with RM inspection details")
    public ResponseEntity<APIResponse> createInspectionCall(@RequestBody CreateInspectionCallRequestDto request) {
        try {
            logger.info("========== CREATE INSPECTION CALL REQUEST ==========");
            logger.info("Request object: {}", request);
            logger.info("Inspection Call: {}", request.getInspectionCall());
            logger.info("RM Details: {}", request.getRmInspectionDetails());
            logger.info("====================================================");

            InspectionCall ic = inspectionCallService.createInspectionCall(
                request.getInspectionCall(),
                request.getRmInspectionDetails()
            );

            logger.info("✅ Inspection call created successfully with ID: {}", ic.getId());
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(ic), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("❌ ERROR creating inspection call", e);
            logger.error("❌ Error message: {}", e.getMessage());
            logger.error("❌ Error cause: {}", e.getCause());

            // Create ErrorDetails object for the response
            ErrorDetails errorDetails = new ErrorDetails(
                AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL,
                AppConstant.ERROR_TYPE_ERROR,
                e.getMessage() != null ? e.getMessage() : "Failed to create inspection call"
            );

            return new ResponseEntity<>(ResponseBuilder.getErrorResponse(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

