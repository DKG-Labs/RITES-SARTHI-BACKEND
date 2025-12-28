package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessShearingDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal lengthCutBar1;
    private BigDecimal lengthCutBar2;
    private BigDecimal lengthCutBar3;
    private Boolean sharpEdges1;
    private Boolean sharpEdges2;
    private Boolean sharpEdges3;
    private Integer rejectedQty1;
    private Integer rejectedQty2;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

