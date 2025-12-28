package com.sarthi.controller;

import com.sarthi.dto.VendorInspectionRequestDto;
import com.sarthi.service.VendorInspectionRequestService;
import com.sarthi.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Vendor Inspection Request operations.
 * All endpoints are JWT protected (configured in SecurityConfig).
 */
@RestController
@RequestMapping("/api/inspection-requests")
public class VendorInspectionRequestController {

    private static final Logger logger = LoggerFactory.getLogger(VendorInspectionRequestController.class);

    @Autowired
    private VendorInspectionRequestService inspectionRequestService;

    /**
     * Create a new inspection request from vendor API data.
     * POST /api/inspection-requests
     */
    @PostMapping
    public ResponseEntity<Object> createInspectionRequest(@RequestBody VendorInspectionRequestDto requestDto) {
        logger.info("Received request to create inspection request for PO: {}", requestDto.getPoNo());
        VendorInspectionRequestDto createdRequest = inspectionRequestService.createInspectionRequest(requestDto);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(createdRequest), HttpStatus.CREATED);
    }

    /**
     * Get inspection request by ID.
     * GET /api/inspection-requests/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInspectionRequestById(@PathVariable Long id) {
        logger.info("Fetching inspection request by ID: {}", id);
        VendorInspectionRequestDto request = inspectionRequestService.getInspectionRequestById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(request), HttpStatus.OK);
    }

    /**
     * Get inspection request by PO Serial Number.
     * GET /api/inspection-requests/po-serial/{poSerialNo}
     */
    @GetMapping("/po-serial/{poSerialNo}")
    public ResponseEntity<Object> getInspectionRequestByPoSerialNo(@PathVariable String poSerialNo) {
        logger.info("Fetching inspection request by PO Serial No: {}", poSerialNo);
        VendorInspectionRequestDto request = inspectionRequestService.getInspectionRequestByPoSerialNo(poSerialNo);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(request), HttpStatus.OK);
    }

    /**
     * Get all inspection requests.
     * GET /api/inspection-requests
     */
    @GetMapping
    public ResponseEntity<Object> getAllInspectionRequests() {
        logger.info("Fetching all inspection requests");
        List<VendorInspectionRequestDto> requests = inspectionRequestService.getAllInspectionRequests();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(requests), HttpStatus.OK);
    }

    /**
     * Get inspection requests by status.
     * GET /api/inspection-requests/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getInspectionRequestsByStatus(@PathVariable String status) {
        logger.info("Fetching inspection requests by status: {}", status);
        List<VendorInspectionRequestDto> requests = inspectionRequestService.getInspectionRequestsByStatus(status);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(requests), HttpStatus.OK);
    }

    /**
     * Get inspection requests by type of call.
     * GET /api/inspection-requests/type/{typeOfCall}
     */
    @GetMapping("/type/{typeOfCall}")
    public ResponseEntity<Object> getInspectionRequestsByTypeOfCall(@PathVariable String typeOfCall) {
        logger.info("Fetching inspection requests by type of call: {}", typeOfCall);
        List<VendorInspectionRequestDto> requests = inspectionRequestService.getInspectionRequestsByTypeOfCall(typeOfCall);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(requests), HttpStatus.OK);
    }

    /**
     * Update inspection request status.
     * PATCH /api/inspection-requests/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> updateInspectionRequestStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String updatedBy) {
        logger.info("Updating status for inspection request ID: {} to {}", id, status);
        VendorInspectionRequestDto updatedRequest = inspectionRequestService.updateInspectionRequestStatus(id, status, updatedBy);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updatedRequest), HttpStatus.OK);
    }

    /**
     * Delete inspection request by ID.
     * DELETE /api/inspection-requests/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInspectionRequest(@PathVariable Long id) {
        logger.info("Deleting inspection request ID: {}", id);
        inspectionRequestService.deleteInspectionRequest(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Inspection request deleted successfully"), HttpStatus.OK);
    }
}

