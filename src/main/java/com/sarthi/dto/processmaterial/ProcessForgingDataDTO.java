package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProcessForgingDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private BigDecimal forgingTemp1;
    private BigDecimal forgingTemp2;
    private BigDecimal forgingTemp3;
    private Integer acceptedQty;
    private Integer rejectedQty;

    // Separate rejection fields for each measurement
    private Integer forgingTemperatureRejected;
    private Integer forgingStabilisationRejected;
    private Integer improperForgingRejected;
    private Integer forgingDefectRejected;
    private Integer embossingDefectRejected;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

