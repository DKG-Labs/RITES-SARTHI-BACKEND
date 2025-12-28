package com.sarthi.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO for per-heat final results including pre-inspection data.
 */
@Data
public class RmHeatFinalResultDto {

    private String inspectionCallNo;
    private Integer heatIndex;
    private String heatNo;

    // Heat Pre-Inspection Data
    private String tcNo;
    private String tcDate;
    private String manufacturerName;
    private String invoiceNumber;
    private String invoiceDate;
    private String subPoNumber;
    private String subPoDate;
    private BigDecimal subPoQty;
    private String totalValueOfPo;
    private BigDecimal tcQuantity;
    private BigDecimal offeredQty;
    private String colorCode;

    // Final Status and Weights
    private String status;
    private BigDecimal weightOfferedMt;
    private BigDecimal weightAcceptedMt;
    private BigDecimal weightRejectedMt;

    // Per-Submodule Status
    private String calibrationStatus;
    private String visualStatus;
    private String dimensionalStatus;
    private String materialTestStatus;
    private String packingStatus;
    private String remarks;
}

