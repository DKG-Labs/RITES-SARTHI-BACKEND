package com.sarthi.dto.rawmaterial;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * DTO for RmHeatQuantity entity.
 * Contains heat-wise quantity breakdown - matches actual database schema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RmHeatQuantityDto {

    private Integer id;
    private Integer rmDetailId;

    /* ==================== Heat Information ==================== */

    private String heatNumber;
    private String manufacturer;

    /* ==================== Quantity Details ==================== */

    private Double offeredQty;

    /* ==================== TC Details ==================== */

    private String tcNumber;
    private String tcDate;
    private Double tcQuantity;

    /* ==================== Inspection Results ==================== */

    private String qtyLeft;
    private String qtyAccepted;
    private String qtyRejected;
    private String rejectionReason;

    /* ==================== Audit Fields ==================== */

    private String createdAt;
    private String updatedAt;

    /* ==================== Nested DTOs ==================== */

    private List<RmChemicalAnalysisDto> chemicalAnalyses;
}

