package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessTurningDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal straightLength1;
    private BigDecimal straightLength2;
    private BigDecimal straightLength3;
    private BigDecimal taperLength1;
    private BigDecimal taperLength2;
    private BigDecimal taperLength3;
    private BigDecimal dia1;
    private BigDecimal dia2;
    private BigDecimal dia3;
    private Integer acceptedQty;
    private Integer parallelLengthRejected;
    private Integer fullTurningLengthRejected;
    private Integer turningDiaRejected;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

