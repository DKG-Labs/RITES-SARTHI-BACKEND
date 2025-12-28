package com.sarthi.dto.rawmaterial;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO for RmChemicalAnalysis entity.
 * Contains chemical composition analysis for each element - matches actual database schema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RmChemicalAnalysisDto {

    private Integer id;
    private Integer heatQuantityId;

    /* ==================== Element Information ==================== */

    private String elementName;
    private String elementSymbol;

    /* ==================== Specified Range ==================== */

    private String specifiedMin;
    private String specifiedMax;

    /* ==================== Actual Values ==================== */

    private String actualValue;
    private String ladleValue;
    private String productValue;

    /* ==================== Audit Fields ==================== */

    private String createdAt;
    private String updatedAt;
}

