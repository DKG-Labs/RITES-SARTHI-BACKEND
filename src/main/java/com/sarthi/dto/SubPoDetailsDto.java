package com.sarthi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Section C: Sub PO Details.
 * Used for API request/response for sub PO / heat data.
 * Multiple SubPoDetails can exist for one inspection call.
 */
@Data
public class SubPoDetailsDto {

    private Long id;

    @NotBlank(message = "Inspection call number is required")
    @Size(max = 50, message = "Inspection call number must not exceed 50 characters")
    private String inspectionCallNo;

    @Size(max = 200, message = "Raw material name must not exceed 200 characters")
    private String rawMaterialName;

    @Size(max = 100, message = "Grade/Spec must not exceed 100 characters")
    private String gradeSpec;

    @Size(max = 100, message = "Heat number must not exceed 100 characters")
    private String heatNo;

    @Size(max = 200, message = "Manufacturer of steel bars must not exceed 200 characters")
    private String manufacturerSteelBars;

    @Size(max = 100, message = "TC number must not exceed 100 characters")
    private String tcNo;

    private LocalDate tcDate;

    @Size(max = 50, message = "Sub PO number must not exceed 50 characters")
    private String subPoNo;

    private LocalDate subPoDate;

    @Size(max = 100, message = "Invoice number must not exceed 100 characters")
    private String invoiceNo;

    private LocalDate invoiceDate;

    private BigDecimal subPoQty;

    @Size(max = 20, message = "Unit must not exceed 20 characters")
    private String unit;

    @Size(max = 200, message = "Place of inspection must not exceed 200 characters")
    private String placeOfInspection;

    /* Approval status: pending, approved, rejected */
    private String status;

    private String rejectionRemarks;

    /* Reference to InspectionCallDetails ID */
    private Long inspectionCallDetailsId;

    /* Audit fields */
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}

