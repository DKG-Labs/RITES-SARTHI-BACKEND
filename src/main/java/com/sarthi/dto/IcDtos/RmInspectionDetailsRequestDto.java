package com.sarthi.dto.IcDtos;

import lombok.Data;

import java.util.List;

@Data
public class RmInspectionDetailsRequestDto {

    private String itemDescription;
    private Integer itemQuantity;
    private String consigneeZonalRailway;

    // ---- HEAT / TC ----
    private String heatNumbers;
    private String tcNumber;
    private String tcDate;          // yyyy-MM-dd
    private Double tcQuantity;

    // ---- SUPPLIER ----
    private String manufacturer;
    private String supplierName;
    private String supplierAddress;

    // ---- INVOICE ----
    private String invoiceNumber;
    private String invoiceDate;     // yyyy-MM-dd

    // ---- SUB PO ----
    private String subPoNumber;
    private String subPoDate;       // yyyy-MM-dd
    private Integer subPoQty;

    private Double totalOfferedQtyMt;
    private Integer offeredQtyErc;
    private String unitOfMeasurement;


    private Double rateOfMaterial;
    private Double rateOfGst;
    private Double baseValuePo;
    private Double totalPo;

    private List<RmHeatQuantityRequestDto> heatQuantities;
    private List<RmChemicalAnalysisRequestDto> chemicalAnalysis;
}
