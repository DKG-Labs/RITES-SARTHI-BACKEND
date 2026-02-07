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
    private BigDecimal quenchingTemperature1;
    private BigDecimal quenchingTemperature2;
    private BigDecimal quenchingDuration1;
    private BigDecimal quenchingDuration2;
    private BigDecimal quenchingHardness1;
    private BigDecimal quenchingHardness2;
    private Integer rejectedQty;

    // Separate rejection fields for each measurement
    private Integer quenchingTemperatureRejected;
    private Integer quenchingDurationRejected;
    private Integer quenchingHardnessRejected;

    // Gauge Check - 2 readings each
    private String boxGauge1;
    private String boxGauge2;
    private Integer boxGaugeRejected;

    private String flatBearingArea1;
    private String flatBearingArea2;
    private Integer flatBearingAreaRejected;

    private String fallingGauge1;
    private String fallingGauge2;
    private Integer fallingGaugeRejected;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

