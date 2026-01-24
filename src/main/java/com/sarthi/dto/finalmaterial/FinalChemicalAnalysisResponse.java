package com.sarthi.dto.finalmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for Final Inspection - Chemical Analysis
 * Returns all chemical analysis data with audit fields
 */
@Data
public class FinalChemicalAnalysisResponse {

    private Long id;
    private String inspectionCallNo;
    private String lotNo;
    private String heatNo;
    private Integer sampleNo;
    private BigDecimal carbonPercent;
    private BigDecimal siliconPercent;
    private BigDecimal manganesePercent;
    private BigDecimal sulphurPercent;
    private BigDecimal phosphorusPercent;
    private String remarks;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}

