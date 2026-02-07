package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessTemperingDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal temperingTemperature1;
    private BigDecimal temperingTemperature2;
    private BigDecimal temperingDuration1;
    private BigDecimal temperingDuration2;
    private Integer acceptedQty;
    private Integer rejectedQty;

    // Separate rejection fields for each measurement
    private Integer temperingTemperatureRejected;
    private Integer temperingDurationRejected;
    private Integer totalTemperingRejection;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

