package com.sarthi.dto.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Section A: Main PO Information
 * Combines data from po_header, po_item, and po_ma_header tables
 */
@Data
public class SectionADataDto {

    // PO Header fields
    private String poNo;
    private LocalDateTime poDate;
    private BigDecimal poQty;
    private String placeOfInspection;
    private String vendorName;
    private String vendorCode;
    private String vendorDetails;
    private String purchasingAuthority;
    private String billPayingOfficer;
    
    // PO Item fields
    private String itemDesc;
    private String consignee;
    private String consigneeDetail;
    private String unit;
    private LocalDateTime deliveryDate;
    private LocalDateTime extendedDeliveryDate;
    
    // MA (Amendment) fields
    private String maNo;
    private String maDate;
    private List<String> maNumbers;
    private List<String> maDates;
    
    // Condition fields
    private String condTitle;
    private String condText;
    
    // Additional fields
    private String rlyCd;
    private String firmDetails;
}

