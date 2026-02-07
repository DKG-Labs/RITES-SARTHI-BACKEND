package com.sarthi.dto.processmaterial;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProcessFinalCheckDataDTO {

    private Long id;
    private String inspectionCallNo;
    private String poNo;
    private String lineNo;
    private String shift;
    private Integer hourIndex;
    private String hourLabel;
    private Boolean noProduction;
    private String lotNo;
    private String boxGauge1;
    private String boxGauge2;
    private String flatBearingArea1;
    private String flatBearingArea2;
    private String fallingGauge1;
    private String fallingGauge2;
    private String surfaceDefect1;
    private String surfaceDefect2;
    private String embossingDefect1;
    private String embossingDefect2;
    private String marking1;
    private String marking2;
    private String temperingHardness1;
    private String temperingHardness2;

    // Separate rejection fields for each measurement
    private Integer boxGaugeRejected;
    private Integer flatBearingAreaRejected;
    private Integer fallingGaugeRejected;
    private Integer surfaceDefectRejected;
    private Integer embossingDefectRejected;
    private Integer markingRejected;
    private Integer temperingHardnessRejected;

    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

