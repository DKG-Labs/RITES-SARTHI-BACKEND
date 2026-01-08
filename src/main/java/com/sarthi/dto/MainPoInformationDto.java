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

    private BigDecimal poQty;

    @Size(max = 200, message = "Place of inspection must not exceed 200 characters")
    private String placeOfInspection;

    @Size(max = 200, message = "Vendor name must not exceed 200 characters")
    private String vendorName;

    @Size(max = 100, message = "Amendment number must not exceed 100 characters")
    private String maNo;

    @Size(max = 255, message = "Amendment date must not exceed 255 characters")
    private String maDate;

    @Size(max = 200, message = "Purchasing authority must not exceed 200 characters")
    private String purchasingAuthority;

    @Size(max = 200, message = "Bill paying officer must not exceed 200 characters")
    private String billPayingOfficer;

    /* Approval status: pending, approved, rejected */
    private String status;

    private String rejectionRemarks;

    /* Audit fields */
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}

