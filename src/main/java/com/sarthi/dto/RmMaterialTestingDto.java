package com.sarthi.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO for material testing samples per heat.
 * Field names match frontend payload (hardnessHrc, decarbDepthMm, inclusionTypeA, etc.)
 */
@Data
public class RmMaterialTestingDto {

    private String inspectionCallNo;
    private String heatNo;
    private Integer heatIndex;
    private Integer sampleNumber;

    // Chemical Composition
    private BigDecimal carbonPercent;
    private BigDecimal siliconPercent;
    private BigDecimal manganesePercent;
    private BigDecimal phosphorusPercent;
    private BigDecimal sulphurPercent;

    // Mechanical Properties - names match frontend payload
    private BigDecimal grainSize;
    private BigDecimal hardnessHrc;
    private BigDecimal decarbDepthMm;

    // Inclusion Ratings with types - names match frontend payload
    private String inclusionTypeA;
    private BigDecimal inclusionA;
    private String inclusionTypeB;
    private BigDecimal inclusionB;
    private String inclusionTypeC;
    private BigDecimal inclusionC;
    private String inclusionTypeD;
    private BigDecimal inclusionD;

    private String remarks;
}

