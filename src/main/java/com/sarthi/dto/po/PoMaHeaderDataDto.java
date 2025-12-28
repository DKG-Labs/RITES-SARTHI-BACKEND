package com.sarthi.dto.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for PO MA (Amendment) Header data (Section B/C)
 * Maps data from po_ma_header table for inspection initiation
 */
@Data
public class PoMaHeaderDataDto {

    private Long id;
    private String maKey;
    
    private String rly;
    private LocalDate maKeyDate;
    
    private String poKey;
    private String poNo;
    
    private String maNo;
    private LocalDate maDate;
    private String maType;
    
    private String vcode;
    private String subject;
    private String remarks;
    
    private String maSignOff;
    private String finStatus;
    private String status;
    
    private String purDiv;
    private String purSec;
    
    private BigDecimal oldPoValue;
    private BigDecimal newPoValue;
    
    private String poMaSrno;
    private String publishFlag;
    
    private LocalDate sent4vet;
    private LocalDate vetDate;
    private String vetBy;
    
    private String sourceSystem;
    
    // Details list
    private List<PoMaDetailDataDto> details;
}

