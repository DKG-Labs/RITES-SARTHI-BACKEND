package com.sarthi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Section B: Inspection Call Details.
 * Used for API request/response for inspection call verification.
 */
@Data
public class InspectionCallDetailsDto {

    private Long id;

    @NotBlank(message = "Inspection call number is required")
    @Size(max = 50, message = "Inspection call number must not exceed 50 characters")
    private String inspectionCallNo;

    private LocalDate inspectionCallDate;

    private LocalDate inspectionDesiredDate;

    /* RLY + PO_NO + PO_SR combined reference */
    @Size(max = 100, message = "RLY PO NO SR must not exceed 100 characters")
    private String rlyPoNoSr;

    private String itemDesc;

    @Size(max = 50, message = "Product type must not exceed 50 characters")
    private String productType;

    private BigDecimal poQty;

    @Size(max = 20, message = "Unit must not exceed 20 characters")
    private String unit;

    @Size(max = 100, message = "Consignee RLY must not exceed 100 characters")
    private String consigneeRly;

    @Size(max = 200, message = "Consignee must not exceed 200 characters")
    private String consignee;

    @Size(max = 50, message = "Original DP must not exceed 50 characters")
    private String origDp;

    @Size(max = 50, message = "Extended DP must not exceed 50 characters")
    private String extDp;

    private LocalDate origDpStart;

    @Size(max = 100, message = "Stage of inspection must not exceed 100 characters")
    private String stageOfInspection;

    private BigDecimal callQty;

    @Size(max = 200, message = "Place of inspection must not exceed 200 characters")
    private String placeOfInspection;

    /* RM IC Number - Only for Process & Final Inspection */
    @Size(max = 100, message = "RM IC number must not exceed 100 characters")
    private String rmIcNumber;

    /* Process IC Number - Only for Final Inspection */
    @Size(max = 100, message = "Process IC number must not exceed 100 characters")
    private String processIcNumber;

    private String remarks;

    /* Approval status: pending, approved, rejected */
    private String status;

    private String rejectionRemarks;

    /* Reference to MainPoInformation ID */
    private Long mainPoId;

    /* Sub PO Details list for Section C */
    private List<SubPoDetailsDto> subPoDetails;

    /* Audit fields */
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}

