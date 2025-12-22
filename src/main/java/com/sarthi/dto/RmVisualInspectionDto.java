package com.sarthi.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO for visual inspection defects per heat.
 */
@Data
public class RmVisualInspectionDto {

    private String inspectionCallNo;
    private String heatNo;
    private Integer heatIndex;
    private String defectName;
    private Boolean isSelected;
    private BigDecimal defectLengthMm;
}

