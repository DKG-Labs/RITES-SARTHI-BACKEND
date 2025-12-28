package com.sarthi.dto.po;

import lombok.Data;

/**
 * DTO for PO MA (Amendment) Detail data
 * Maps data from po_ma_detail table for inspection initiation
 */
@Data
public class PoMaDetailDataDto {

    private Long id;
    private String maKey;
    
    private String rly;
    
    private String slno;
    private String maFld;
    private String maFldDescr;
    
    private String oldValue;
    private String newValue;
    
    private String newValueInd;
    private String newValueFlag;
    
    private String plNo;
    private String poSr;
    
    private String condSlno;
    private String condCode;
    
    private String maSrNo;
    private String status;
    
    private String sourceSystem;
}

