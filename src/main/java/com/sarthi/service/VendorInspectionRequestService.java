package com.sarthi.service;

import com.sarthi.dto.VendorInspectionRequestDto;
import java.util.List;

/**
 * Service interface for Vendor Inspection Request operations.
 * Defines business logic contracts for inspection request management.
 */
public interface VendorInspectionRequestService {

    /**
     * Create a new inspection request with heat TC mappings
     * @param requestDto The inspection request data from vendor API
     * @return Created inspection request DTO
     */
    VendorInspectionRequestDto createInspectionRequest(VendorInspectionRequestDto requestDto);

    /**
     * Get inspection request by ID
     * @param id The inspection request ID
     * @return Inspection request DTO
     */
    VendorInspectionRequestDto getInspectionRequestById(Long id);

    /**
     * Get inspection request by PO serial number
     * @param poSerialNo The PO serial number
     * @return Inspection request DTO
     */
    VendorInspectionRequestDto getInspectionRequestByPoSerialNo(String poSerialNo);

    /**
     * Get all inspection requests
     * @return List of inspection request DTOs
     */
    List<VendorInspectionRequestDto> getAllInspectionRequests();

    /**
     * Get all inspection requests by status
     * @param status The status to filter by
     * @return List of inspection request DTOs
     */
    List<VendorInspectionRequestDto> getInspectionRequestsByStatus(String status);

    /**
     * Get all inspection requests by type of call
     * @param typeOfCall The type of call to filter by
     * @return List of inspection request DTOs
     */
    List<VendorInspectionRequestDto> getInspectionRequestsByTypeOfCall(String typeOfCall);

    /**
     * Update inspection request status
     * @param id The inspection request ID
     * @param status The new status
     * @param updatedBy The user making the update
     * @return Updated inspection request DTO
     */
    VendorInspectionRequestDto updateInspectionRequestStatus(Long id, String status, String updatedBy);

    /**
     * Delete inspection request by ID
     * @param id The inspection request ID
     */
    void deleteInspectionRequest(Long id);
}

