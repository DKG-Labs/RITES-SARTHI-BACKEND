package com.sarthi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for available heat numbers with inventory information.
 * Used to display only heat numbers that have remaining stock available.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableHeatNumberDto {

    private Long id;
    
    // Heat & TC Information
    private String heatNumber;
    private String tcNumber;
    private String tcDate;
    private String manufacturer;
    
    // Quantity Information
    private BigDecimal tcQuantity;           // Total TC quantity
    private BigDecimal tcQtyRemaining;       // Remaining quantity available
    private BigDecimal offeredQty;           // Previously offered quantity
    
    // Sub PO Information
    private String subPoNumber;
    private String subPoDate;
    private String subPoQty;
    private String subPoTotalValue;
    
    // Invoice Information
    private String invoiceNo;
    private String invoiceDate;
    
    // Material Information (from inventory)
    private String rawMaterial;
    private String gradeSpecification;
    
    // Status
    private String status;
    private boolean isAvailable;             // Computed field: true if tcQtyRemaining > 0
}

