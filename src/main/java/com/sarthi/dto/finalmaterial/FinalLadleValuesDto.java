package com.sarthi.dto.finalmaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO for Final Inspection Ladle Values
 * Represents chemical composition values from vendor's ladle analysis
 * Used to display ladle values in Final Chemical Analysis page
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalLadleValuesDto {

    private String lotNo;
    private String heatNo;
    private BigDecimal percentC;
    private BigDecimal percentSi;
    private BigDecimal percentMn;
    private BigDecimal percentP;
    private BigDecimal percentS;
}

