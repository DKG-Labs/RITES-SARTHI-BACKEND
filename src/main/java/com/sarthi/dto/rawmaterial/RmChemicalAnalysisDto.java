package com.sarthi.dto.rawmaterial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for Raw Material Chemical Analysis
 * Used to return chemical composition data for auto-fetching in inspection
 * calls
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmChemicalAnalysisDto {

    private String heatNumber;
    private BigDecimal carbon;
    private BigDecimal manganese;
    private BigDecimal silicon;
    private BigDecimal sulphur;
    private BigDecimal phosphorus;
    private BigDecimal chromium;
}
