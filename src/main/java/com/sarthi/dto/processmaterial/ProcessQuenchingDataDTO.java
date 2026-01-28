package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessQuenchingDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal quenchingTemperature;
    private BigDecimal quenchingDuration;
    private BigDecimal quenchingHardness1;
    private BigDecimal quenchingHardness2;
    private Integer rejectedQty;

    // Separate rejection fields for each measurement
    private Integer quenchingTemperatureRejected;
    private Integer quenchingDurationRejected;
    private Integer quenchingHardnessRejected;
    private Integer boxGaugeRejected;
    private Integer flatBearingAreaRejected;
    private Integer fallingGaugeRejected;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

