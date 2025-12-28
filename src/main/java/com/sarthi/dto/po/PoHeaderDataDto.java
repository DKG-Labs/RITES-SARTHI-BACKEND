package com.sarthi.dto.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for PO Header data (Section A)
 * Maps data from po_header table for inspection initiation
 */
@Data
public class PoHeaderDataDto {

    private Long id;
    private String poKey;
    private String poNo;
    private String l5PoNo;
    
    private String rlyCd;
    private String rlyShortName;
    
    private String purchaserCode;
    private String purchaserDetail;
    
    private String stockNonStock;
    private String rlyNonRly;
    private String poOrLetter;
    
    private String vendorCode;
    private String vendorDetails;
    private String firmDetails;
    
    private String inspectingAgency;
    private String poStatus;
    
    private LocalDateTime poDate;
    private LocalDateTime receivedDate;
    
    private String userId;
    private String sourceSystem;
    
    // Items list
    private List<PoItemDataDto> items;
}

