package com.sarthi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for Heat Number to TC mapping data.
 * Used for API request/response handling.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmHeatTcMappingDto {

    private Long id;
    
    // Heat & TC Information
    private String heatNumber;
    private String tcNumber;
    private String tcDate;
    private String manufacturer;
    
    // Invoice Information
    private String invoiceNo;
    private String invoiceDate;
    
    // Sub PO Information
    private String subPoNumber;
    private String subPoDate;
    private String subPoQty;
    private String subPoTotalValue;
    
    // TC Quantities
    private String tcQty;
    private String tcQtyRemaining;
    private String offeredQty;
}

