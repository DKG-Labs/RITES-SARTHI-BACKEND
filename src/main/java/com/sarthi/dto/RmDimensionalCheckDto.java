package com.sarthi.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO for dimensional check samples per heat.
 */
@Data
public class RmDimensionalCheckDto {

    private String inspectionCallNo;
    private String heatNo;
    private Integer heatIndex;
    private Integer sampleNumber;
    private BigDecimal diameter;
}

