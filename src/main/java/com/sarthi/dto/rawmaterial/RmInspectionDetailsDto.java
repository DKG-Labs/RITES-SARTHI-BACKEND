package com.sarthi.dto.rawmaterial;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * DTO for RmInspectionDetails entity.
 * Contains RM-specific inspection details - matches actual database schema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RmInspectionDetailsDto {

    private Integer id;
    private Integer icId;

    /* ==================== Item Details ==================== */

    private String itemDescription;
    private String itemQuantity;
    private String consigneeZonalRailway;
    private String heatNumbers;

    /* ==================== TC Details ==================== */

    private String tcNumber;
    private String tcDate;
    private String tcQuantity;

    /* ==================== Manufacturer/Supplier Details ==================== */

    private String manufacturer;
    private String supplierName;
    private String supplierAddress;

    /* ==================== Invoice Details ==================== */

    private String invoiceNumber;
    private String invoiceDate;

    /* ==================== Sub PO Details ==================== */

    private String subPoNumber;
    private String subPoDate;
    private String subPoQty;

    /* ==================== Quantity Details ==================== */

    private String totalOfferedQtyMt;
    private String offeredQtyErc;
    private String unitOfMeasurement;

    /* ==================== Rate Details ==================== */

    private String rateOfMaterial;
    private String rateOfGst;
    private String baseValuePo;
    private String totalPo;

    /* ==================== Audit Fields ==================== */

    private String createdAt;
    private String updatedAt;

    /* ==================== Nested DTOs ==================== */

    private List<RmHeatQuantityDto> rmHeatQuantities;
}

