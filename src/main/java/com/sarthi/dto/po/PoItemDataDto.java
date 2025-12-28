package com.sarthi.dto.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for PO Item data (Section A)
 * Maps data from po_item table for inspection initiation
 */
@Data
public class PoItemDataDto {

    private Long id;
    
    private String rly;
    private String caseNo;
    private String itemSrNo;
    private String plNo;
    private String itemDesc;
    
    // Consignee
    private String consigneeCd;
    private String immsConsigneeCd;
    private String immsConsigneeName;
    private String consigneeDetail;
    
    // Quantity & UOM
    private Integer qty;
    private Integer qtyCancelled;
    private String uomCd;
    private String uom;
    
    // Financials
    private BigDecimal rate;
    private BigDecimal basicValue;
    private BigDecimal salesTaxPercent;
    private BigDecimal salesTax;
    private String discountType;
    private BigDecimal discountPercent;
    private BigDecimal discount;
    private BigDecimal value;
    private String otChargeType;
    private BigDecimal otChargePercent;
    private BigDecimal otherCharges;
    
    // Dates
    private LocalDateTime deliveryDate;
    private LocalDateTime extendedDeliveryDate;
    
    // Misc
    private String allocation;
    private String userId;
    private String sourceSystem;
}

