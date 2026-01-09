package com.sarthi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for Vendor Inspection Request data.
 * Maps JSON structure from vendor API for request/response handling.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorInspectionRequestDto {

    private Long id;
    
    // PO Information
    private String poNo;
    private String poSerialNo;
    private String poDate;
    private String poDescription;
    private Integer poQty;
    private String poUnit;
    private String amendmentNo;
    private String amendmentDate;

    // Vendor Information
    private String vendorCode;
    private String vendorContactName;
    private String vendorContactPhone;
    
    // Inspection Call Info
    private String typeOfCall;
    private String desiredInspectionDate;
    
    // Already Inspected Quantities
    private Integer qtyAlreadyInspectedRm;
    private Integer qtyAlreadyInspectedProcess;
    private Integer qtyAlreadyInspectedFinal;
    
    // Raw Material Heat Numbers (comma separated)
    private String rmHeatNumbers;
    
    // Heat TC Mapping List
    private List<RmHeatTcMappingDto> rmHeatTcMapping;
    
    // Chemical Composition
    private BigDecimal rmChemicalCarbon;
    private BigDecimal rmChemicalManganese;
    private BigDecimal rmChemicalSilicon;
    private BigDecimal rmChemicalSulphur;
    private BigDecimal rmChemicalPhosphorus;
    private BigDecimal rmChemicalChromium;
    
    // Offered Quantities
    private BigDecimal rmTotalOfferedQtyMt;
    private Integer rmOfferedQtyErc;
    
    // Company Information
    private Integer companyId;
    private String companyName;
    private String cin;
    
    // Unit Information
    private Integer unitId;
    private String unitName;
    private String unitAddress;
    private String unitGstin;
    private String unitContactPerson;
    private String unitRole;

    // PO Additional Information
    private String purchasingAuthority;
    private String bpo;
    private String deliveryPeriod;
    private String inspectionFeesPaymentDetails;

    // Remarks
    private String remarks;
    
    // Status
    private String status;
    
    // Audit Fields
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
}

