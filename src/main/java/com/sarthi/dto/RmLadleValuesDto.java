package com.sarthi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for Ladle Values (Chemical Analysis) from vendor
 * Used in Material Testing page to display ladle values
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RmLadleValuesDto {

    private String heatNo;
    
    // Chemical composition percentages
    private BigDecimal percentC;      // Carbon
    private BigDecimal percentSi;     // Silicon
    private BigDecimal percentMn;     // Manganese
    private BigDecimal percentP;      // Phosphorus
    private BigDecimal percentS;      // Sulphur
    private BigDecimal percentCr;     // Chromium (optional)
}

