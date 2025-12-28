package com.sarthi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Section A: Main PO Information.
 * Used for API request/response for PO data verification.
 */
@Data
public class MainPoInformationDto {

    private Long id;

    @NotBlank(message = "Inspection call number is required")
    @Size(max = 50, message = "Inspection call number must not exceed 50 characters")
    private String inspectionCallNo;

    @Size(max = 50, message = "PO number must not exceed 50 characters")
    private String poNo;

    private LocalDate poDate;

    @Size(max = 50, message = "Vendor code must not exceed 50 characters")
    private String vendorCode;

    @Size(max = 200, message = "Vendor name must not exceed 200 characters")
    private String vendorName;

    private String vendorAddress;

    @Size(max = 200, message = "Place of inspection must not exceed 200 characters")
    private String placeOfInspection;

    @Size(max = 200, message = "Manufacturer must not exceed 200 characters")
    private String manufacturer;

    @Size(max = 100, message = "Consignee RLY must not exceed 100 characters")
    private String consigneeRly;

    @Size(max = 200, message = "Consignee must not exceed 200 characters")
    private String consignee;

    private String itemDescription;

    private BigDecimal poQty;

    @Size(max = 20, message = "Unit must not exceed 20 characters")
    private String unit;

    @Size(max = 50, message = "Original DP must not exceed 50 characters")
    private String origDp;

    @Size(max = 50, message = "Extended DP must not exceed 50 characters")
    private String extDp;

    private LocalDate origDpStart;

    @Size(max = 200, message = "BPO must not exceed 200 characters")
    private String bpo;

    private LocalDate dateOfInspection;

    @Size(max = 20, message = "Shift of inspection must not exceed 20 characters")
    private String shiftOfInspection;

    private BigDecimal offeredQty;

    /* Approval status: pending, approved, rejected */
    private String status;

    private String rejectionRemarks;

    /* Audit fields */
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}

